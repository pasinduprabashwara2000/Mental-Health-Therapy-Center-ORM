module edu.ijse.fx.layered.orm {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;

    opens edu.ijse.fx.layered.orm to javafx.fxml;
    opens edu.ijse.fx.layered.orm.controller to javafx.fxml;
    opens edu.ijse.fx.layered.orm.dto to javafx.base;
    opens edu.ijse.fx.layered.orm.entity to org.hibernate.orm.core;

    exports edu.ijse.fx.layered.orm;
}
