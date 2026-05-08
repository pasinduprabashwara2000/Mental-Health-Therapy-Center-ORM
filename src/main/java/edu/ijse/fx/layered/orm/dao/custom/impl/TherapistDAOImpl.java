package edu.ijse.fx.layered.orm.dao.custom.impl;

import edu.ijse.fx.layered.orm.config.FactoryConfiguration;
import edu.ijse.fx.layered.orm.dao.custom.TherapistDAO;
import edu.ijse.fx.layered.orm.entity.TherapistEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;

public class TherapistDAOImpl implements TherapistDAO {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(TherapistEntity therapistEntity) throws Exception {

        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {

        } catch (Exception e) {

        } finally {

        }

        return false;
    }

    @Override
    public boolean update(TherapistEntity therapistEntity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return false;
    }

    @Override
    public boolean search(Integer id) throws Exception {
        return false;
    }

    @Override
    public ArrayList<TherapistEntity> getAll() throws Exception {
        return null;
    }
}
