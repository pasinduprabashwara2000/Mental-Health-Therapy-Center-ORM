package edu.ijse.fx.layered.orm.bo.custom;

import edu.ijse.fx.layered.orm.bo.SuperBO;
import edu.ijse.fx.layered.orm.dto.LoginDTO;

public interface LoginBO extends SuperBO {

    LoginDTO findUsernameAndPassword(String username, String password) throws Exception;

}
