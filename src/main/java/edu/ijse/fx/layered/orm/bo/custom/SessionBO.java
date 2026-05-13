package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;
import edu.ijse.fx.layered.orm.dto.SessionDTO;
import java.util.ArrayList;

public interface SessionBO extends SuperBO {

    boolean save(SessionDTO sessionDTO) throws Exception;
    boolean update(SessionDTO sessionDTO) throws Exception;
    boolean delete(String id) throws Exception;
    SessionDTO search(String id) throws Exception;
    ArrayList<SessionDTO> getAll() throws Exception;

}
