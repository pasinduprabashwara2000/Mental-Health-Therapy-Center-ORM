package edu.ijse.fx.layered.orm.bo;

import edu.ijse.fx.layered.orm.bo.custom.impl.PatientBOImpl;
import edu.ijse.fx.layered.orm.bo.custom.impl.ProgramBOImpl;
import edu.ijse.fx.layered.orm.bo.custom.impl.TherapistBOImpl;

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
        THERAPIST, PROGRAM, PATIENT
    }

    public SuperBO getBO (BOTypes boTypes){
        switch (boTypes){
            case THERAPIST :
                return new TherapistBOImpl();
            case PROGRAM :
                return new ProgramBOImpl();
            case PATIENT:
                return new PatientBOImpl();
        }

        return null;
    }

}
