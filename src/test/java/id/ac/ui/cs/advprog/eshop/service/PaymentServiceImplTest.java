package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        payments = new ArrayList<>();
        Payment payment1 = new Payment("g197ru40-2c48-7hd3-1b64-0nc85fbcf9e1", PaymentStatus.REJECTED.getValue());
        payments.add(payment1);
        Payment payment2 = new Payment("js1396af-d79b-e5f6-c3d4-56789abcdef0", PaymentStatus.REJECTED.getValue());
        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.addPayment(payment);
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testAddPaymentIfAlreadyExists() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertNull(paymentService.addPayment(payment));
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    void testAddPaymentMoreThanOne() {
        Payment payment1 = new Payment("g197ru40-2c48-7hd3-1b64-0nc85fbcf9e1", PaymentStatus.REJECTED.getValue());
        paymentService.addPayment(payment1);
        Payment payment2 = new Payment("js1396af-d79b-e5f6-c3d4-56789abcdef0", PaymentStatus.REJECTED.getValue());
        paymentService.addPayment(payment2);

        doReturn(resultPayments).when(paymentRepository).findAll();
        List<Payment> resultPayments = paymentService.getAllPayments();
        assertEquals(resultPayments.size(), 2);
        assertEquals(resultPayments.get(0).getId(), payment1.getId());
        assertEquals(resultPayments.get(1).getId(), payment2.getId());

    }

    @Test
    void testUpdateStatus() {
        Payment payment = payments.get(1);
        Payment newPayment = new Payment("g197ru40-2c48-7hd3-1b64-0nc85fbcf9e1", PaymentStatus.REJECTED.getValue());
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(newPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment.getId(), PaymentStatus.SUCCESS.getValue());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment.getId(), "INVALID"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidPaymentId() {
        doReturn(null).when(paymentRepository).findById("zczc");

        assertThrows(NoSuchElementException.class,
                () -> paymentService.setStatus("zczc", PaymentStatus.SUCCESS.getValue()));
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);
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