package edu.ijse.fx.layered.orm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/edu/ijse/fx/layered/orm/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 666, 500);
        stage.setTitle("Welcome !");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
