package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository{

    private List<Product> productData = new ArrayList<>();
    public Product create(Product product){
        if(product.getProductId() == null){
            UUID uuid = UUID.randomUUID();
            product.setProductId(uuid.toString());
        }
        productData.add(product);
        return product;
    }

    public Product find(String productId){
        Boolean valid = false;
        Product product = new Product();
        for(int i=0; i < productData.size(); i++){
            if(productData.get(i).getProductId().equals(productId)){
                product = productData.get(i);
                productData.remove(i);
                valid = true;
            }
        }
        if(valid){
            return product;
        }
        return null;
    }

    public void save(Product product){
        productData.add(product);
    }

    public void delete(String productId){
        for(int i=0; i < productData.size(); i++){
            if(productData.get(i).getProductId() == productId){
                productData.remove(i);
            }
        }
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }
}