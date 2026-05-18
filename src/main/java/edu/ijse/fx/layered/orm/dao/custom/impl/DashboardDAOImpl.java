package edu.ijse.fx.layered.orm.dao.custom.impl;

import edu.ijse.fx.layered.orm.config.FactoryConfiguration;
import edu.ijse.fx.layered.orm.dao.custom.DashboardDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DashboardDAOImpl implements DashboardDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public int findTherapistCount() throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Long count = (Long) session.createQuery("SELECT COUNT(*) FROM TherapistEntity").uniqueResult();
            transaction.commit();
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0;
        } finally {
            session.close();
        }

    }

    @Override
    public int findProgramsCount() throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Long count = (Long)session.createQuery("SELECT COUNT(*) FROM ProgramEntity").uniqueResult();
            transaction.commit();
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0;
        }

    }

    @Override
    public int findSessionsCount() throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Long count = (Long) session.createQuery("SELECT COUNT(*) FROM SessionEntity").uniqueResult();
            transaction.commit();
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0;
        }
    }

    @Override
    public double findTotalRevenue() throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Double profit = (Double)session.createQuery("SELECT SUM(amount) FROM PaymentEntity").uniqueResult();
            transaction.commit();
            return profit.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0.0;
        } finally {
            session.close();
        }
    }
}
