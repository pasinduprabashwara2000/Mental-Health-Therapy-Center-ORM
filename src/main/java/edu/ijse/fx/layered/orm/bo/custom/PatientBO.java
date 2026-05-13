package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;
import edu.ijse.fx.layered.orm.dto.PatientDTO;
import java.util.ArrayList;

public interface PatientBO extends SuperBO {

    boolean save(PatientDTO patientDTO) throws Exception;
    boolean update(PatientDTO patientDTO) throws Exception;
    boolean delete(String id) throws Exception;
    PatientDTO search(String id) throws Exception;
    ArrayList<PatientDTO> getAll() throws Exception;

}
