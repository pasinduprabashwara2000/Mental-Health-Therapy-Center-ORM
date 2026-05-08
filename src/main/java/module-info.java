module edu.ijse.fx.layered.orm {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens edu.ijse.fx.layered.orm to javafx.fxml;
    opens edu.ijse.fx.layered.orm.controller to javafx.fxml;

    exports edu.ijse.fx.layered.orm;
}