package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.DashboardBO;
import edu.ijse.fx.layered.orm.dao.DAOFactory;
import edu.ijse.fx.layered.orm.dao.custom.DashboardDAO;

public class DashboardBOImpl implements DashboardBO {

    private final DashboardDAO dashboardDAO = (DashboardDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.DASHBOARD);

    @Override
    public int findTherapistCount() throws Exception {
        return dashboardDAO.findTherapistCount();
    }

    @Override
    public int findProgramsCount() throws Exception {
        return dashboardDAO.findProgramsCount();
    }

    @Override
    public int findSessionsCount() throws Exception {
        return dashboardDAO.findSessionsCount();
    }

    @Override
    public double findTotalRevenue() throws Exception {
        return dashboardDAO.findTotalRevenue();
    }
}
