package edu.ijse.fx.layered.orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientEntity {

    @Id
    private String patientId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 2)
    private int age;

    @Column(nullable = false, length = 5)
    private String gender;

    @Column(nullable = false, unique = true ,name = "contact_no", length = 10)
    private String contactNumber;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 255)
    private String disease;

    @OneToMany(mappedBy = "patientId")
    private List<SessionEntity> sessions;

}
