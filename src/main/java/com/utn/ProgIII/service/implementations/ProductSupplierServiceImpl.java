package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.csv.CsvReader;
import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.exceptions.ProductSupplierNotExistException;
import com.utn.ProgIII.exceptions.SupplierNotFoundException;
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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
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

    @Override
    public SupplierProductListDTO listProductsBySupplier(String companyName) {

        Supplier supplier = supplierRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new SupplierNotFoundException("El proveedor no existe"));

        List<?> priceList = new ArrayList<>();

        if(authService.hasRole("ROLE_MANAGER")){
            BigDecimal dolar = miscService.searchDollarPrice().venta();

            priceList = productSupplierRepository.productsBySupplierManager(supplier.getIdSupplier(),dolar);
        } else if (authService.hasRole("ROLE_EMPLOYEE")) {
            priceList = productSupplierRepository.productsBySupplierEmployee(supplier.getIdSupplier());
        }


        return new SupplierProductListDTO(
                supplier.getIdSupplier(),
                supplier.getCompanyName(),
                priceList
        );

    }

    public ProductPricesDTO listPricesByProduct(Long idProduct) {
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new ProductNotFoundException("El producto no existe"));

        List<?> priceList = new ArrayList<>();

        if(authService.hasRole("ROLE_MANAGER")){
            BigDecimal dolar = miscService.searchDollarPrice().venta();

            priceList = productSupplierRepository.listPricesByProductManager(idProduct,dolar);
        } else if (authService.hasRole("ROLE_EMPLOYEE")) {
            priceList = productSupplierRepository.listPricesByProductEmployee(idProduct);
        }

        return new ProductPricesDTO(product.getIdProduct(),product.getName(),priceList);
    }

    @Override
    public String uploadCsv(String filepath, Long idSupplier) {
        return csvReader.uploadToDatabase(filepath,idSupplier);
    }

    @Override
    public String uploadCsv(String filepath, Long idSupplier, BigDecimal bulkProfitMargin) {

        if(0 > bulkProfitMargin.compareTo(BigDecimal.valueOf(0)))
        {
            throw new InvalidRequestException("El porcentaje de ganancia no es valido");
        }

        return csvReader.uploadToDatabase(filepath,idSupplier,bulkProfitMargin);
    }

    @Override
    public ResponseProductSupplierDTO findById(Long id) {
        return productSupplierRepository.findById(id)
                .map(mapper::fromEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("ProductSupplier not found with id: " + id));
    }
}
