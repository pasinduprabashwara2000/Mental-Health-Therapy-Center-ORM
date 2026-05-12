package edu.ijse.fx.layered.orm.dao.custom.impl;

import edu.ijse.fx.layered.orm.config.FactoryConfiguration;
import edu.ijse.fx.layered.orm.dao.custom.PatientDAO;
import edu.ijse.fx.layered.orm.entity.PatientEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;

public class PatientDAOImpl implements PatientDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(PatientEntity patientEntity) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(patientEntity);
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
    public boolean update(PatientEntity patientEntity) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            PatientEntity oldPatient = session.find(PatientEntity.class, patientEntity.getPatientId());
            oldPatient.setName(patientEntity.getName());
            oldPatient.setAge(patientEntity.getAge());
            oldPatient.setGender(patientEntity.getGender());
            oldPatient.setAddress(patientEntity.getAddress());
            oldPatient.setDisease(patientEntity.getDisease());
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
           PatientEntity patientEntity = session.find(PatientEntity.class, id);
           session.remove(patientEntity);
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
    public PatientEntity search(String id) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            PatientEntity patientEntity = session.find(PatientEntity.class, id);
            transaction.commit();
            return patientEntity;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<PatientEntity> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();
        try {
            return new ArrayList<>(session.createQuery("from PatientEntity",PatientEntity.class).list());
        } finally {
            session.close();
        }
    }
}
