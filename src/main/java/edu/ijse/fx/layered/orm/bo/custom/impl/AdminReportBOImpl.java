package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.AdminReportBO;
import edu.ijse.fx.layered.orm.dao.DAOFactory;
import edu.ijse.fx.layered.orm.dao.custom.AdminReportDAO;
import edu.ijse.fx.layered.orm.dto.AdminReportDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class AdminReportBOImpl implements AdminReportBO {

    private final AdminReportDAO adminReportDAO = (AdminReportDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ADMINREPORT);

    @Override
    public ArrayList<AdminReportDTO> getTherapistPerformance(LocalDate from, LocalDate to, String therapistId) throws Exception {
        return adminReportDAO.getTherapistPerformance(from,to,therapistId);
    }

    @Override
    public Map<String, Long> getSessionStatusStats(LocalDate from, LocalDate to) throws Exception {
        return adminReportDAO.getSessionStatusStats(from,to);
    }

    @Override
    public ArrayList<String> getAllTherapistIds() throws Exception {
        return adminReportDAO.getAllTherapistIds();
    }
}
