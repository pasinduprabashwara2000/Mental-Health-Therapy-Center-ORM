package edu.ijse.fx.layered.orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "program")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false , length = 255)
    private String name;

    @Column(nullable = false, length = 25)
    private String duration;

    @Column(nullable = false, length = 25)
    private double cost;

    @Column(nullable = false, length = 255)
    private String description;

}
