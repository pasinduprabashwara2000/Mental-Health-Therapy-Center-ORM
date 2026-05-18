package edu.ijse.fx.layered.orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentEntity {

    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @OneToOne
    @JoinColumn(name = "session_id")
    private SessionEntity sessionId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false, name = "payment_method")
    private String paymentMethod;

    @Column(nullable = false, name = "payment_date")
    private LocalDate paymentDate;

    @Column(nullable = false)
    private String status;

}
