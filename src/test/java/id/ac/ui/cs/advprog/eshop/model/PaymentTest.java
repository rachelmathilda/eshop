package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Order order;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentNoStatus(){
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode");
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
    }

    @Test
    void testCreatePaymentSuccessStatus(){
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", PaymentStatus.SUCCESS.getValue());
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", "INVALID");
        });
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", "SUCCESS");
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", PaymentStatus.SUCCESS.getValue());
        assertThrows(IllegalArgumentException.class, () -> order.setStatus("INVALID"));
    }
}