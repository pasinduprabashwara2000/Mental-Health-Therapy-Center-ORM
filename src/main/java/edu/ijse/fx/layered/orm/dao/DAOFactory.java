package edu.ijse.fx.layered.orm.dao;

import edu.ijse.fx.layered.orm.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getInstance(){
        if(daoFactory == null){
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes{
        THERAPIST, PROGRAM, PATIENT, SESSION, PAYMENT, DASHBOARD, LOGIN
    }

    public SuperDAO getDAO (DAOTypes daoTypes){
        switch (daoTypes){
            case THERAPIST :
                return new TherapistDAOImpl();
            case PROGRAM :
                return new ProgramDAOImpl();
            case PATIENT :
                return new PatientDAOImpl();
            case SESSION :
                return new SessionDAOImpl();
            case PAYMENT :
                return new PaymentDAOImpl();
            case DASHBOARD :
                return new DashboardDAOImpl();
            case LOGIN :
                return new LoginDAOImpl();
        }
        return null;
    }

}
