module edu.ijse.fx.layered.orm {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;
    requires java.desktop;
    requires java.xml;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires net.sf.jasperreports.pdf;

    opens edu.ijse.fx.layered.orm to javafx.fxml;
    opens edu.ijse.fx.layered.orm.controller to javafx.fxml;
    opens edu.ijse.fx.layered.orm.entity to org.hibernate.orm.core;
    opens edu.ijse.fx.layered.orm.dto;

    exports edu.ijse.fx.layered.orm;
}