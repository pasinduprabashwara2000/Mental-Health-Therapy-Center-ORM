package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;
import edu.ijse.fx.layered.orm.dto.AdminReportDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public interface AdminReportBO extends SuperBO {

    ArrayList<AdminReportDTO> getTherapistPerformance(LocalDate from, LocalDate to, String therapistId) throws Exception;
    Map<String, Long> getSessionStatusStats(LocalDate from, LocalDate to) throws Exception;
    ArrayList<String> getAllTherapistIds() throws Exception;

}