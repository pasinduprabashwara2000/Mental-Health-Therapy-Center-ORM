package edu.ijse.fx.layered.orm.config;

import edu.ijse.fx.layered.orm.entity.TherapistEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    public FactoryConfiguration(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClasses(TherapistEntity.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance(){
        if (factoryConfiguration == null){
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }

}
