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
        if(method.equals("voucher") ){
            if(paymentData.containsKey("voucherCode")){
                String voucherCode = paymentData.get("voucherCode");
                if (voucherCode.length() == 16 &&
                        voucherCode.startsWith("ESHOP") &&
                        voucherCode.chars().filter(Character::isDigit).count() == 8){
                    setStatus(payment, "SUCCESS");
                } else {
                    setStatus(payment, "REJECTED");
                }
            } else {
                setStatus(payment, "REJECTED");
            }
        } else if (method.equals("cod")) {
            if(paymentData.containsKey("address") && paymentData.containsKey("deliveryFee")){
                String address = paymentData.get("address");
                String deliveryFee = paymentData.get("deliveryFee");
                if (address != null && !address.isEmpty() &&
                        deliveryFee != null && !deliveryFee.isEmpty()) {
                    setStatus(payment, "SUCCESS");
                    payment.setPaymentData(paymentData);
                    setStatus(payment, "SUCCESS");
                } else {
                    setStatus(payment, "REJECTED");
                }
            } else {
                setStatus(payment, "REJECTED");
            }

        }
        payment.setPaymentData(paymentData);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status){
        if(orderRepository.findById(payment.getId()) != null){
            Order order = orderRepository.findById(payment.getId());
            order.setStatus(status);
            payment.setStatus(status);
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
