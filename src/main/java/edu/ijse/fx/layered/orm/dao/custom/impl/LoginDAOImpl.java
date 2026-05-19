package edu.ijse.fx.layered.orm.dao.custom.impl;

import edu.ijse.fx.layered.orm.config.FactoryConfiguration;
import edu.ijse.fx.layered.orm.dao.custom.LoginDAO;
import edu.ijse.fx.layered.orm.entity.LoginEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class LoginDAOImpl implements LoginDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public LoginEntity findUsernameAndPassword(String username, String password) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<LoginEntity> query = session.createQuery("FROM LoginEntity WHERE username = :username AND Password = :password", LoginEntity.class);
            query.setParameter("username",username);
            query.setParameter("password",password);
            LoginEntity login = query.uniqueResult();
            transaction.commit();
            return login;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }
}
