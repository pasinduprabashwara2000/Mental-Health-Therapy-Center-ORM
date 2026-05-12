package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;
import edu.ijse.fx.layered.orm.dto.PatientsDTO;
import java.util.ArrayList;

public interface PatientBO extends SuperBO {

    boolean save(PatientsDTO patientsDTO) throws Exception;
    boolean update(PatientsDTO patientsDTO) throws Exception;
    boolean delete(String id) throws Exception;
    PatientsDTO search(String id) throws Exception;
    ArrayList<PatientsDTO> getAll() throws Exception;

}
