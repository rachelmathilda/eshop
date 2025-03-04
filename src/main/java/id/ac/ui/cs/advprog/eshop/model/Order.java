package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Order {
    private String id;
    private List<Product> products;
    private Long orderTime;
    private String author;
    private String status;

    public enum status {
        WAITING_PAYMENT,
        FAILED,
        CANCELLED,
        SUCCESS
    }

    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
        setProducts(products);
        this.orderTime = orderTime;
        setAuthor(author);
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
        setProducts(products);
        this.orderTime = orderTime;
        setAuthor(author);
        setStatus(status);
    }
}
