package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData){
        Payment payment = new Payment(order.getId());
        setStatus(payment, "SUCCESS");
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status){
        if(orderRepository.findById(payment.getId()) != null){
            Order order = orderRepository.findById(payment.getId());
            order.setStatus(status);
            return payment;
        } else {
            return payment;
        }
    }

    @Override
    public Payment getPayment(String paymentId){
        return paymentRepository.findById(paymentId);
    };

    @Override
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }
}
