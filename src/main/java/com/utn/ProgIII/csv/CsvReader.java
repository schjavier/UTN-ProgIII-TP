package com.utn.ProgIII.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.utn.ProgIII.dto.ProductInfoFromCsvDTO;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.repository.ProductSupplierRepository;
import com.utn.ProgIII.repository.SupplierRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {
    private final SupplierRepository supplierRepository;
    private final ProductSupplierRepository productSupplierRepository;
    private final ProductRepository productRepository;

    public CsvReader(ProductRepository productRepository, SupplierRepository supplierRepository, ProductSupplierRepository productSupplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.productSupplierRepository = productSupplierRepository;
    }

    public static List<ProductInfoFromCsvDTO> readFile(String path) throws IOException {
        File csvFile = new File(path);
        CsvSchema schema = CsvSchema.builder()
                .addColumn("id")
                .addColumn("nombre")
                .addColumn("precio")
                .setSkipFirstDataRow(true)
                .build();

        CsvMapper mapper = new CsvMapper();
        MappingIterator<ProductInfoFromCsvDTO> productIterator = mapper
                .readerFor(ProductInfoFromCsvDTO.class)
                .with(schema)
                .readValues(csvFile);

        return productIterator.readAll();
    }

    public String uploadToDatabase(String csvFilePath, Long supplierId) {
        StringBuilder message = new StringBuilder("Productos no subidos: \n");
        List<ProductInfoFromCsvDTO> uploads;
        List<ProductInfoFromCsvDTO> failedUploads = new ArrayList<>();

        try {
            uploads = readFile(csvFilePath).stream().toList();
            Supplier supplierData = supplierRepository.getReferenceById(supplierId);

            for (ProductInfoFromCsvDTO productUpdateInfo: uploads) {
                Product productData = productRepository.getByName(productUpdateInfo.name());
                ProductSupplier relationship = productSupplierRepository.getByProductAndSupplier(productData,supplierData);
                if (productData != null && productData.getStatus().equals(ProductStatus.ENABLED) && relationship != null) {
                    relationship = updateRelationshipPricing(productUpdateInfo,relationship);
                    productSupplierRepository.save(relationship);
                } else {
                    failedUploads.add(productUpdateInfo);
                }
            }
        } catch (IOException e) {
            System.out.println("Error procesando el archivo: " + e.getMessage());
        }

        failedUploads.forEach(failedUpload -> message.append(failedUpload.name()).append("\n"));
        return message.toString();
    }

    public String uploadToDatabase(String csvFilePath, Long supplierId, BigDecimal bulkProfitMargin) {
        StringBuilder message = new StringBuilder("Productos no subidos: \n");
        List<ProductInfoFromCsvDTO> uploads;
        List<ProductInfoFromCsvDTO> failedUploads = new ArrayList<>();

        try {
            uploads = readFile(csvFilePath).stream().toList();
            Supplier supplierData = supplierRepository.getReferenceById(supplierId);

            for (ProductInfoFromCsvDTO productUpdateInfo: uploads) {
                Product productData = productRepository.getByName(productUpdateInfo.name());
                ProductSupplier relationship = productSupplierRepository.getByProductAndSupplier(productData,supplierData);

                if (productData != null && productData.getStatus().equals(ProductStatus.ENABLED)) {
                    if (relationship == null) {
                        relationship = new ProductSupplier(supplierData,productData,productUpdateInfo.cost(),bulkProfitMargin);
                    } else {
                        relationship = updateRelationshipPricing(productUpdateInfo, relationship);
                    }
                    productSupplierRepository.save(relationship);
                } else {
                    failedUploads.add(productUpdateInfo);
                }
            }
        } catch (IOException e) {
            System.out.println("Error procesando el archivo: " + e.getMessage());
        }

        failedUploads.forEach(failedUpload -> message.append(failedUpload.name()).append("\n"));
        return message.toString();
    }

    public ProductSupplier updateRelationshipPricing(ProductInfoFromCsvDTO productUpdateInfo, ProductSupplier relationship) {
        relationship.setCost(productUpdateInfo.cost());
        relationship.setPrice(relationship.getCost()
                .add(relationship.getCost()
                        .multiply(relationship.getProfitMargin())
                        .divide(BigDecimal.valueOf(100), RoundingMode.CEILING)));

        return relationship;
    }
}
