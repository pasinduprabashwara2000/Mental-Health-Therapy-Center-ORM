package edu.ijse.fx.layered.orm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminReportDTO {

    private String therapistId;
    private String therapistName;
    private int totalSessions;
    private int completedSessions;
    private int cancelledSessions;
    private double performancePercent;


}
