package edu.ijse.fx.layered.orm.dao.custom;

import edu.ijse.fx.layered.orm.dao.SuperDAO;
import edu.ijse.fx.layered.orm.entity.LoginEntity;

public interface LoginDAO extends SuperDAO {

    LoginEntity findUsernameAndPassword(String username, String password) throws Exception;

}
