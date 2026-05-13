package edu.ijse.fx.layered.orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "session")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionEntity {

    @Id
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private TherapistEntity therapistId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patientId;

    @Column(nullable = false)
    private LocalDate date;

}
