package edu.ijse.fx.layered.orm.dao;

import edu.ijse.fx.layered.orm.dao.custom.impl.TherapistDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public enum DAOTypes{
        THERAPIST
    }

    public SuperDAO getDAO (DAOTypes daoTypes){
        switch (daoTypes){
            case THERAPIST :
                return new TherapistDAOImpl();
        }
        return null;
    }

}
