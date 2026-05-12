package edu.ijse.fx.layered.orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "therapist")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TherapistEntity {

    @Id
    private String therapistId;

    @Column(nullable = false, length = 100)
    private String therapistName;

    @OneToOne
    @JoinColumn(name = "program_id")
    private ProgramEntity programId;

    @Column(nullable = false, length = 255)
    private String specialization;

    @Column(name = "contact_no", unique = true, length = 10)
    private int contactNo;

}
