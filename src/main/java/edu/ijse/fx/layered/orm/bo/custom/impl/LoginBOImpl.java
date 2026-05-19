package edu.ijse.fx.layered.orm.bo.custom.impl;

import edu.ijse.fx.layered.orm.bo.custom.LoginBO;
import edu.ijse.fx.layered.orm.dao.DAOFactory;
import edu.ijse.fx.layered.orm.dao.custom.LoginDAO;
import edu.ijse.fx.layered.orm.dto.LoginDTO;
import edu.ijse.fx.layered.orm.entity.LoginEntity;

public class LoginBOImpl implements LoginBO {

    private final LoginDAO loginDAO = (LoginDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public LoginDTO findUsernameAndPassword(String username, String password) throws Exception {

        LoginEntity loginEntity = loginDAO.findUsernameAndPassword(username,password);

        if(loginEntity != null){
            return new LoginDTO(
                    loginEntity.getId(),
                    loginEntity.getUsername(),
                    loginEntity.getPassword(),
                    loginEntity.getRole()
            );
        }
        return null;
    }
}
