package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;
import edu.ijse.fx.layered.orm.dto.TherapistDTO;
import java.util.ArrayList;

public interface TherapistBO extends SuperBO {

    boolean save(TherapistDTO therapistDTO) throws Exception;
    boolean update(TherapistDTO therapistDTO) throws Exception;
    boolean delete(Integer id) throws Exception;
    TherapistDTO search(Integer id) throws Exception;
    ArrayList<TherapistDTO> getAll() throws Exception;

}
