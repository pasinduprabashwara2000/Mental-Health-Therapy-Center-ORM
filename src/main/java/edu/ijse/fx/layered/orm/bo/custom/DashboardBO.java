package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;

public interface DashboardBO extends SuperBO {

    int findTherapistCount() throws Exception;
    int findProgramsCount() throws Exception;
    int findSessionsCount() throws Exception;
    double findTotalRevenue() throws Exception;

}
