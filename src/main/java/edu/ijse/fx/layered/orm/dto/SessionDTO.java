package edu.ijse.fx.layered.orm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionDTO {

    private String sessionsId;
    private String therapistId;
    private String patientsId;
    private LocalDate date;
    private LocalTime time;

}
