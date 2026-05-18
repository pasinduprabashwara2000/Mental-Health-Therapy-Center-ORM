package edu.ijse.fx.layered.orm.dao.custom.impl;

import edu.ijse.fx.layered.orm.config.FactoryConfiguration;
import edu.ijse.fx.layered.orm.dao.custom.PaymentDAO;
import edu.ijse.fx.layered.orm.entity.PaymentEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(PaymentEntity paymentEntity) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(paymentEntity);
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
    public boolean update(PaymentEntity paymentEntity) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            PaymentEntity oldPayment = session.find(PaymentEntity.class,paymentEntity.getPaymentId());
            oldPayment.setSessionId(paymentEntity.getSessionId());
            oldPayment.setAmount(paymentEntity.getAmount());
            oldPayment.setPaymentMethod(paymentEntity.getPaymentMethod());
            oldPayment.setPaymentDate(paymentEntity.getPaymentDate());
            oldPayment.setStatus(paymentEntity.getStatus());
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
        Transaction transaction= session.beginTransaction();
        try {
            PaymentEntity paymentEntity = session.find(PaymentEntity.class, id);
            session.remove(paymentEntity);
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
    public PaymentEntity search(String id) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            PaymentEntity paymentEntity = session.find(PaymentEntity.class,id);
            transaction.commit();
            return paymentEntity;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<PaymentEntity> getAll() throws Exception {

        Session session = factoryConfiguration.getSession();
        try {
            return new ArrayList<PaymentEntity>(session.createQuery("FROM PaymentEntity", PaymentEntity.class).list());
        } finally {
            session.close();
        }
    }
}
