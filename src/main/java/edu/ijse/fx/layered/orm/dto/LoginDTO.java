package edu.ijse.fx.layered.orm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {

    private int id;
    private String username;
    private String Password;
    private String role;

}
