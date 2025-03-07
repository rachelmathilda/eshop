package id.ac.ui.cs.advprog.eshop.model;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Getter
@Setter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id) {
        this.id = id;
    }

    public Payment(String id, String status) {
        this.id = id;
        setStatus(status);
    }

    public Payment(String id, String status, String method) {
        this.id = id;
        this.method = method;
        setStatus(status);
    }

    public void setStatus(String status) {
        String[] statusList = {"SUCCESS", "REJECTED"};
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
