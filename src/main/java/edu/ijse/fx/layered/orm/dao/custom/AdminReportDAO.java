package edu.ijse.fx.layered.orm.dao.custom;

import edu.ijse.fx.layered.orm.dao.SuperDAO;
import edu.ijse.fx.layered.orm.dto.AdminReportDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public interface AdminReportDAO extends SuperDAO {

    ArrayList<AdminReportDTO> getTherapistPerformance(LocalDate from, LocalDate to, String therapistId) throws Exception;
    Map<String, Long> getSessionStatusStats(LocalDate from, LocalDate to) throws Exception;
    ArrayList<String> getAllTherapistIds() throws Exception;
    ArrayList<String> getAllSessionStatuses() throws Exception;
}