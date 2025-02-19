package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Autowired
    ProductController productController;

    @Test
    void testShowAllProducts() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        List<Product> products = List.of(product);

        when(productService.findAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", products))
                .andExpect(view().name("ProductList"));
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(productService.findAll()).thenReturn(List.of(product));
        when(productService.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productName", product.getProductName())
                .param("productQuantity", String.valueOf(product.getProductQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:list"));
    }

    @Test
    void testUpdateProductPage() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);

        mockMvc.perform(get("/product/edit/{productId}", product.getProductId()))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attribute("product", product));
    }

    @Test
    void testUpdateProductPage_NotFound() throws Exception {
        when(productService.findById("notexist-id")).thenThrow(ProductRepository.ProductNotFoundException.class);

        mockMvc.perform(get("/product/edit/notexist-id"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/ProductNotFound"));
    }

    @Test
    void testUpdateProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);

        when(productService.findById(product.getProductId())).thenReturn(product);
        when(productService.update(any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(post("/product/edit/{productId}", product.getProductId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productName", updatedProduct.getProductName())
                        .param("productQuantity", String.valueOf(updatedProduct.getProductQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testUpdateProductPost_NotFound() throws Exception {
        Product product = new Product();
        product.setProductId("notexist-id");

        when(productService.update(any(Product.class))).thenThrow(ProductRepository.ProductNotFoundException.class);

        mockMvc.perform(post("/product/edit/notexist-id")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productName", "Test Product") //
                        .param("productQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/ProductNotFound"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        mockMvc.perform(get("/product/delete/{id}", product.getProductId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));
    }
}
