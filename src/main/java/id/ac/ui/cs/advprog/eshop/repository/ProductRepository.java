package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository{
    private long id = 0;
    private List<Product> productData = new ArrayList<>();
    public Product create(Product product){
        product.setProductId(id);
        id += 1;
        productData.add(product);
        return product;
    }

    public Product find(Long productId){
        Boolean valid = false;
        Product product = new Product();
        for(int i=0; i < productData.size(); i++){
            if(productData.get(i).getProductId() == productId){
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

    public void delete(Long productId){
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