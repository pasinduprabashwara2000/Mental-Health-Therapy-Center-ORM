package edu.ijse.fx.layered.orm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDTO {

    private String paymentId;
    private String sessionId;
    private double amount;
    private String paymentMethod;
    private LocalDate paymentDate;
    private String status;

}
