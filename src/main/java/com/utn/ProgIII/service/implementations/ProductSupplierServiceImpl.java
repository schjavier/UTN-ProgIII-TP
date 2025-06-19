package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.csv.CsvReader;
import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.exceptions.*;
import com.utn.ProgIII.mapper.ProductSupplierMapper;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.ProductSupplier.*;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.repository.ProductSupplierRepository;
import com.utn.ProgIII.repository.SupplierRepository;
import com.utn.ProgIII.service.interfaces.AuthService;
import com.utn.ProgIII.service.interfaces.MiscService;
import com.utn.ProgIII.service.interfaces.ProductSupplierService;
import com.utn.ProgIII.validations.ProductSupplierValidations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
/**
 * Un servicio que se encarga de hacer funciones para la relacion entre productos y proveedores
 */
public class ProductSupplierServiceImpl implements ProductSupplierService {

    private final ProductSupplierRepository productSupplierRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductSupplierMapper mapper;
    private final ProductSupplierValidations productSupplierValidations;
    private final CsvReader csvReader;
    private final AuthService authService;
    private final MiscService miscService;

    public ProductSupplierServiceImpl(ProductSupplierRepository productSupplierRepository,
                                      ProductRepository productRepository,
                                      SupplierRepository supplierRepository,
                                      ProductSupplierMapper mapper,
                                      ProductSupplierValidations productSupplierValidations,
                                      CsvReader csvReader,
                                      AuthService authService,
                                      MiscService miscService
    ){

        this.productSupplierRepository = productSupplierRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
        this.productSupplierValidations = productSupplierValidations;
        this.csvReader = csvReader;
        this.authService = authService;
        this.miscService = miscService;
    }


    /**
     * Se crea una nueva relacion entre un producto y proveedor, con su precio y margen de ganancia
     * @param createProductSupplierDTO Un dto que se usa crear una relacion
     * @return Un dto de la relacion entre el producto y proveedor creada
     */
    @Override
    public ResponseProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO) {

        Supplier supplier = supplierRepository.findById(createProductSupplierDTO.idSupplier())
                .orElseThrow(() -> new SupplierNotFoundException("No existe el proveedor con ese ID"));

        Product product = productRepository.findById(createProductSupplierDTO.idProduct())
                .orElseThrow( ()-> new ProductNotFoundException("No existe producto con ese ID"));

        ProductSupplier productSupplier = new ProductSupplier(
                supplier,
                product,
                createProductSupplierDTO.cost(),
                createProductSupplierDTO.profitMargin()
        );

        productSupplierValidations.validateRelationship(productSupplier);

        productSupplierRepository.save(productSupplier);

        return mapper.fromEntityToDto(productSupplier);
    }

    /**
     * Se actualiza una relacion existente, con sus datos
     * @param updateProductSupplierDTO Un objeto con los datos para modificar la relacion
     * @param id El id para la relacion a editar
     * @return Un dto con los datos modificados
     */
    @Override
    public ResponseProductSupplierDTO updateProductSupplier(UpdateProductSupplierDTO updateProductSupplierDTO, Long id) {

        ProductSupplier productSupplier = productSupplierRepository.findById(id)
                .orElseThrow(() -> new ProductSupplierNotExistException("La relación que quiere editar no se encuentra"));

        BigDecimal newCost = updateProductSupplierDTO.cost();
        BigDecimal newProfitMargin = updateProductSupplierDTO.profitMargin();

        productSupplier.setCost(newCost);
        productSupplier.setProfitMargin(newProfitMargin);
        productSupplier.setPrice(newCost.add(newCost.multiply(newProfitMargin).divide(BigDecimal.valueOf(100), RoundingMode.CEILING)));

        productSupplierRepository.save(productSupplier);

        return mapper.fromEntityToDto(productSupplier);
    }

    /**
     * Lista las relaciones segun el nombre de la empresa, los datos mostrados varian segun el nivel de acceso del usuario
     * @param companyName Nombre de proveedor para buscar
     * @return Una lista de dtos con los datos para mostrar
     */
    @Override
    public SupplierProductListDTO listProductsBySupplier(String companyName) {

        Supplier supplier = supplierRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new SupplierNotFoundException("El proveedor no existe"));

        List<?> priceList = new ArrayList<>();


        if (!authService.isEmployee()) {
            try {
                BigDecimal dolar = miscService.searchDollarPrice().venta();

                priceList = productSupplierRepository.productsBySupplierManager(supplier.getIdSupplier(),dolar);
            } catch (UnexpectedServerErrorException e) {
                priceList = productSupplierRepository.productsBySupplierManagerFallback(supplier.getIdSupplier());
            }
        } else {
            priceList = productSupplierRepository.productsBySupplierEmployee(supplier.getIdSupplier());
        }


        return new SupplierProductListDTO(
                supplier.getIdSupplier(),
                supplier.getCompanyName(),
                priceList
        );

    }

    /**
     * Lista las relaciones segun el id de un producto cargado, los datos mostrados varian segun el nivel de acceso del usuario
     * @param idProduct El id del producto
     * @return Una lista de dtos para mostrar
     */
    public ProductPricesDTO listPricesByProduct(Long idProduct) {
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new ProductNotFoundException("El producto no existe"));

        List<?> priceList = new ArrayList<>();

      
        if(!authService.isEmployee()){
            try {
                BigDecimal dolar = miscService.searchDollarPrice().venta();

                priceList = productSupplierRepository.listPricesByProductManager(idProduct,dolar);
            } catch (UnexpectedServerErrorException e) {
                priceList = productSupplierRepository.listPricesByProductManagerFallback(idProduct);
            }
        } else {
           priceList = productSupplierRepository.listPricesByProductEmployee(idProduct);
        }

        return new ProductPricesDTO(product.getIdProduct(),product.getName(),priceList);
    }

    /**
     * Se usa para modificar relaciones existentes
     * @param filepath Path interno del archivo
     * @param idSupplier El id del proveedor para modificar sus relaciones
     * @return
     */
    @Override
    public String uploadCsv(String filepath, Long idSupplier) {
        return csvReader.uploadToDatabase(filepath,idSupplier);
    }

    /**
     * Se usa para cargar relaciones nuevas y modifican el precio de las existentes
     * @param filepath Path interno del archivo
     * @param idSupplier El id del proveedor para modificar sus relaciones
     * @param bulkProfitMargin El margen de ganancia
     * @return Un mensaje de error diciendo que productos no fueron cargados
     * @see CsvReader
     */
    @Override
    public String uploadCsv(String filepath, Long idSupplier, BigDecimal bulkProfitMargin) {

        if(0 > bulkProfitMargin.compareTo(BigDecimal.valueOf(0)))
        {
            throw new InvalidRequestException("El porcentaje de ganancia no es valido");
        }

        return csvReader.uploadToDatabase(filepath,idSupplier,bulkProfitMargin);
    }

}
