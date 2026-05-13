package edu.ijse.fx.layered.orm.dto;

import edu.ijse.fx.layered.orm.entity.SessionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientDTO {

    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String contactNumber;
    private String address;
    private String disease;
    private List<SessionEntity> sessions;

}