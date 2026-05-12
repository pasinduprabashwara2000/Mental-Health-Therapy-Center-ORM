package edu.ijse.fx.layered.orm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientsDTO {

    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String contactNumber;
    private String address;
    private String disease;

}
