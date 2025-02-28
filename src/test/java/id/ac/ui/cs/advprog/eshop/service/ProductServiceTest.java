package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        List<Product> productList = List.of(product);
        when(productRepository.findAll()).thenReturn(productList.iterator());

        productService.create(product);

        List<Product> retrievedProductList = productService.findAll();
        assertFalse(retrievedProductList.isEmpty());
        Product savedProduct = retrievedProductList.get(0);
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productService.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);
        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);
        productService.update(updatedProduct);

        when(productRepository.findById(product.getProductId())).thenReturn(updatedProduct);
        Product retrievedProduct = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(updatedProduct.getProductName(), retrievedProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), retrievedProduct.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        List<Product> productList = List.of(product);
        when(productRepository.findAll()).thenReturn(productList.iterator());
        productService.create(product);

        List<Product> retrievedProductList = productService.findAll();
        assertFalse(retrievedProductList.isEmpty());

        doNothing().when(productRepository).delete(product);
        productService.delete(product);

        List<Product> deletedProductList = productService.findAll();
        assertTrue(deletedProductList.isEmpty());
    }
}