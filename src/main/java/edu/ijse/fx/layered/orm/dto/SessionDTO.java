package edu.ijse.fx.layered.orm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionDTO {

    private String sessionId;
    private String therapistId;
    private String patientId;
    private LocalDate date;
    private String time;
    private String status;

}
