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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String therapistName;

    @Column(nullable = false, length = 100)
    private String programName;

    @Column(nullable = false, length = 255)
    private String Specialization;

    @Column(name = "contact_no", unique = true, length = 10)
    private int ContactNo;

}
