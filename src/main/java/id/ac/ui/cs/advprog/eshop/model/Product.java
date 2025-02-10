package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String id) {
        productId = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        productName = name;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int quantity) {
        productQuantity = quantity;
    }
}