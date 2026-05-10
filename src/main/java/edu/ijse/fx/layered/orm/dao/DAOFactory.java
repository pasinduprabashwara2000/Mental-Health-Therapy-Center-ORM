package edu.ijse.fx.layered.orm.dao;

import edu.ijse.fx.layered.orm.dao.custom.impl.ProgramDAOImpl;
import edu.ijse.fx.layered.orm.dao.custom.impl.TherapistDAOImpl;

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
        THERAPIST, PROGRAM
    }

    public SuperDAO getDAO (DAOTypes daoTypes){
        switch (daoTypes){
            case THERAPIST :
                return new TherapistDAOImpl();
            case PROGRAM :
                return new ProgramDAOImpl();
        }
        return null;
    }

}
