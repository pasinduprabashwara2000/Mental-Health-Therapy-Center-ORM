package edu.ijse.fx.layered.orm.bo;

import edu.ijse.fx.layered.orm.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getInstance(){
        if(boFactory == null){
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes {
        THERAPIST, PROGRAM, PATIENT, SESSION, PAYMENT, DASHBOARD, LOGIN
    }

    public SuperBO getBO (BOTypes boTypes){
        switch (boTypes){
            case THERAPIST :
                return new TherapistBOImpl();
            case PROGRAM :
                return new ProgramBOImpl();
            case PATIENT:
                return new PatientBOImpl();
            case SESSION:
                return new SessionBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            case LOGIN :
                return new LoginBOImpl();
        }

        return null;
    }

}
