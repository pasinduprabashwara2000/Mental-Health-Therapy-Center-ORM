package edu.ijse.fx.layered.orm.dao.custom;

import edu.ijse.fx.layered.orm.dao.SuperDAO;

public interface DashboardDAO extends SuperDAO {

    int findTherapistCount() throws Exception;
    int findProgramsCount() throws Exception;
    int findSessionsCount() throws Exception;
    double findTotalRevenue() throws Exception;

}
