package edu.ijse.fx.layered.orm.dao.custom.impl;

import edu.ijse.fx.layered.orm.config.FactoryConfiguration;
import edu.ijse.fx.layered.orm.dao.custom.ProgramDAO;
import edu.ijse.fx.layered.orm.entity.ProgramEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;

public class ProgramDAOImpl implements ProgramDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(ProgramEntity programEntity) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(programEntity);
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
    public boolean update(ProgramEntity programEntity) throws Exception {

        Session session= factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            ProgramEntity oldProgramEntity = session.find(ProgramEntity.class, programEntity.getId());
            oldProgramEntity.setName(programEntity.getName());
            oldProgramEntity.setDuration(programEntity.getDuration());
            oldProgramEntity.setCost(programEntity.getCost());
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
            ProgramEntity programEntity = session.find(ProgramEntity.class, id);
            session.remove(programEntity);
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
    public ProgramEntity search(String id) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            ProgramEntity programEntity = session.find(ProgramEntity.class, id);
            transaction.commit();
            return programEntity;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<ProgramEntity> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();

        try {
            return new ArrayList<>(session.createQuery("from ProgramEntity",ProgramEntity.class).list());
        } finally {
            session.close();
        }
    }
}
