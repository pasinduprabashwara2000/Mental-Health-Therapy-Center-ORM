package edu.ijse.fx.layered.orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "login")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginEntity {

    @Id
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String Password;

    @Column(nullable = false)
    private String role;

}
