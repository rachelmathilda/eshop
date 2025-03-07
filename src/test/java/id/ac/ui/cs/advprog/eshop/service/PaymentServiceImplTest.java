package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;
    List<Payment> payments;
    OrderRepository orderRepository;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orderRepository = new OrderRepository();
        orders = new ArrayList<>();
        Order order1 = new Order("g197ru40-2c48-7hd3-1b64-0nc85fbcf9e1",
                products, 1708560000L, "Safira Sudrajat");
        orderRepository.save(order1);
        orders.add(order1);
        Order order2 = new Order("js1396af-d79b-e5f6-c3d4-56789abcdef0",
                products, 1708570000L, "Safira Sudrajat");
        orderRepository.save(order2);
        orders.add(order2);

        payments = new ArrayList<>();
        Payment payment1 = new Payment("g197ru40-2c48-7hd3-1b64-0nc85fbcf9e1", PaymentStatus.REJECTED.getValue());
        payments.add(payment1);
        Payment payment2 = new Payment("js1396af-d79b-e5f6-c3d4-56789abcdef0", PaymentStatus.REJECTED.getValue());
        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).save(payment);

        Order order = orders.getFirst();
        Map<String, String> map = new HashMap<String, String>();
        Payment result = paymentService.addPayment(order, "voucher", map);
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testAddPaymentIfAlreadyExists() {
        Payment payment = payments.getFirst();
        Order order = orders.getFirst();
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Map<String, String> map = new HashMap<String, String>();
        assertNull(paymentService.addPayment(order, "voucher", map));
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    void testAddPaymentMoreThanOne() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("voucherCode", "ESHOP9876XYZ1234");
        Payment payment1 = new Payment("g197ru40-2c48-7hd3-1b64-0nc85fbcf9e1", PaymentStatus.REJECTED.getValue());
        Order order1 = orders.getFirst();
        doReturn(payment1).when(paymentRepository).save(payment1);
        paymentService.addPayment(order1, "voucher", map);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("address", "depok");      // Delivery address
        map2.put("deliveryFee", "12.000");
        Payment payment2 = new Payment("js1396af-d79b-e5f6-c3d4-56789abcdef0", PaymentStatus.REJECTED.getValue());
        Order order2 = orders.get(1);
        doReturn(payment2).when(paymentRepository).save(payment2);
        paymentService.addPayment(order2, "cod", map2);

        doReturn(payments).when(paymentRepository).findAll();
        List<Payment> resultPayments = paymentService.getAllPayments();
        assertEquals(resultPayments.size(), 2);
        assertEquals(resultPayments.get(0).getId(), payment1.getId());
        assertEquals(resultPayments.get(1).getId(), payment2.getId());
    }

    @Test
    void testUpdateStatus() {
        Payment payment = payments.getFirst();
        Payment newPayment = new Payment("g197ru40-2c48-7hd3-1b64-0nc85fbcf9e1", PaymentStatus.REJECTED.getValue());
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(newPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "INVALID"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidPaymentId() {
        doReturn(null).when(paymentRepository).findById("zczc");

        Payment payment = new Payment("zczc");
        assertThrows(NoSuchElementException.class,
                () -> paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue()));
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");
        assertNull(paymentService.getPayment("zczc"));
    }
}