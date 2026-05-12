package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;
import edu.ijse.fx.layered.orm.dto.ProgramDTO;
import java.util.ArrayList;

public interface ProgramBO extends SuperBO {

    boolean save(ProgramDTO programDTO) throws Exception;
    boolean update(ProgramDTO programDTO) throws Exception;
    boolean delete(String id) throws Exception;
    ProgramDTO search(String id) throws Exception;
    ArrayList<ProgramDTO> getAll() throws Exception;

}
