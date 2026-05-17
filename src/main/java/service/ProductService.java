package service;

import model.Product;
import util.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private static final String FILE_NAME = "product.txt";

    public boolean createProduct(Product product) {
        product.setProductId(FileDatabase.getNextId(FILE_NAME));
        String[] record = {
                String.valueOf(product.getProductId()),
                String.valueOf(product.getSellerId()),
                product.getProductName(),
                product.getDescription(),
                String.valueOf(product.getPrice()),
                String.valueOf(product.getQuantity()),
                product.getImageUrl(),
                String.valueOf(product.isActive())
        };
        return FileDatabase.addRecord(FILE_NAME, record);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        for (String[] record : FileDatabase.readAll(FILE_NAME)) {
            Product p = mapToProduct(record);
            if (p != null) products.add(p);
        }
        return products;
    }

    public Product getProductById(int productId) {
        for (String[] record : FileDatabase.readAll(FILE_NAME)) {
            if (FileDatabase.safeParseInt(record[0]) == productId) {
                return mapToProduct(record);
            }
        }
        return null;
    }

    public List<Product> getProductsBySellerId(int sellerId) {
        List<Product> products = new ArrayList<>();
        for (String[] record : FileDatabase.readAll(FILE_NAME)) {
            if (record.length > 1 && FileDatabase.safeParseInt(record[1]) == sellerId) {
                Product p = mapToProduct(record);
                if (p != null) products.add(p);
            }
        }
        return products;
    }

    public boolean updateProduct(Product product) {
        String[] record = {
                String.valueOf(product.getProductId()),
                String.valueOf(product.getSellerId()),
                product.getProductName(),
                product.getDescription(),
                String.valueOf(product.getPrice()),
                String.valueOf(product.getQuantity()),
                product.getImageUrl(),
                String.valueOf(product.isActive())
        };
        return FileDatabase.updateRecord(FILE_NAME, String.valueOf(product.getProductId()), record);
    }

    public boolean deleteProduct(int productId) {
        return FileDatabase.deleteRecord(FILE_NAME, String.valueOf(productId));
    }

    private Product mapToProduct(String[] record) {
        if (record.length < 8) return null;
        try {
            int productId = FileDatabase.safeParseInt(record[0]);
            int sellerId  = FileDatabase.safeParseInt(record[1]);
            if (productId <= 0) return null;
            Product product = new Product();
            product.setProductId(productId);
            product.setSellerId(sellerId);
            product.setProductName(record[2]);
            product.setDescription(record[3]);
            product.setPrice(FileDatabase.safeParseDouble(record[4]));
            product.setQuantity(FileDatabase.safeParseInt(record[5]));
            product.setImageUrl(record[6]);
            product.setActive(Boolean.parseBoolean(record[7].trim()));
            return product;
        } catch (Exception e) {
            System.err.println("Error parsing product record: " + String.join("|", record));
            return null;
        }
    }
}
