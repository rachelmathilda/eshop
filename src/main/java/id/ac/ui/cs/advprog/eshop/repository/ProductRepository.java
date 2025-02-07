package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Optional;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product){
        product.setProductId(generateId());
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }

    public Product findById(String id){
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }

    public Product update(Product product){
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(product.getProductId())){
                productData.set(i, product);
                return productData.get(i);
            }
        }
        throw new ProductNotFoundException("Product with ID " + product.getProductId() + " not found.");
    }

    public String generateId(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for (int i = 0; i < 8; i++){
            int randomIndex = random.nextInt(chars.length); // Generates random int within the list size
            sb.append(chars[randomIndex]);
        }

        sb.append("-");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int randomIndex = random.nextInt(chars.length); // Generates random int within the list size
                sb.append(chars[randomIndex]);
            }
            sb.append("-");
        }

        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(chars.length); // Generates random int within the list size
            sb.append(chars[randomIndex]);
        }

        return sb.toString();
    }
  
    public class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
  
    public void delete(Product product){
        for (int i = 0; i < productData.size(); i++) {
            Product piece = productData.get(i);
            if (product.getProductId().equals(piece.getProductId())) {
                productData.remove(i);
                return;
            }
        }
    }
}