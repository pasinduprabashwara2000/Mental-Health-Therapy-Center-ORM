package edu.ijse.fx.layered.orm.dao.custom.impl;

import edu.ijse.fx.layered.orm.config.FactoryConfiguration;
import edu.ijse.fx.layered.orm.dao.custom.SessionDAO;
import edu.ijse.fx.layered.orm.entity.SessionEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;

public class SessionDAOImpl implements SessionDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(SessionEntity sessionEntity) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(sessionEntity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(SessionEntity sessionEntity) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            SessionEntity oldSession = session.find(SessionEntity.class, sessionEntity.getSessionId());
            oldSession.getTherapistId();
            oldSession.getPatientId();
            oldSession.getDate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            SessionEntity sessionEntity = session.find(SessionEntity.class, id);
            session.remove(sessionEntity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public SessionEntity search(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            SessionEntity sessionEntity = session.find(SessionEntity.class,id);
            if(sessionEntity != null){
                transaction.commit();
                return sessionEntity;
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public ArrayList<SessionEntity> getAll() throws Exception {

        Session session = factoryConfiguration.getSession();
        try {
            return new ArrayList<>(session.createQuery("from SessionEntity", SessionEntity.class).list());
        } finally {
            session.close();
        }
    }
}
