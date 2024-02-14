package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService{
    public Product create(Product product);
    public Product getProduct(Long productId);
    public void updateProduct(Product product);
    public void deleteProduct(Long productId);
    public List<Product> findAll();
}