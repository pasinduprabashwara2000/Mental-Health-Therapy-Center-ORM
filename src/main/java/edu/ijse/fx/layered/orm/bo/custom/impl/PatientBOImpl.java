package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.PatientBO;
import edu.ijse.fx.layered.orm.dto.PatientsDTO;
import java.util.ArrayList;

public class PatientBOImpl implements PatientBO {

    @Override
    public boolean save(PatientsDTO patientsDTO) throws Exception {
        return false;
    }

    @Override
    public boolean update(PatientsDTO patientsDTO) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public PatientsDTO search(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<PatientsDTO> getAll() throws Exception {
        return null;
    }
}
