package edu.ijse.fx.layered.orm.config;

import edu.ijse.fx.layered.orm.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(TherapistEntity.class);
        configuration.addAnnotatedClasses(ProgramEntity.class);
        configuration.addAnnotatedClasses(SessionEntity.class);
        configuration.addAnnotatedClasses(PatientEntity.class);
        configuration.addAnnotatedClasses(PaymentEntity.class);
        configuration.addAnnotatedClasses(LoginEntity.class);

        sessionFactory = configuration.buildSessionFactory();

    }

    public static FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}