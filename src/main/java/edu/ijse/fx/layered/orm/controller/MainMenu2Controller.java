package edu.ijse.fx.layered.orm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainMenu2Controller {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button patientsBtn;

    @FXML
    private Button paymentsBtn;

    @FXML
    private Button sessionsBtn;

    @FXML
    public void initialize() {
        patientsBtn.setOnAction(event -> loadUI("ManagePatients"));
        sessionsBtn.setOnAction(event -> loadUI("ManageSessions"));
        paymentsBtn.setOnAction(event -> loadUI("ManagePayments"));
    }

    private void loadUI(String fxmlName) {
        try {
            String fxmlPath = "/edu/ijse/fx/layered/orm/" + fxmlName + ".fxml";
            URL fxmlLocation = getClass().getResource(fxmlPath);
            Parent root = FXMLLoader.load(fxmlLocation);
            contentPane.getChildren().setAll(root);

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlName);
            e.printStackTrace();
        }
    }

    public void navigateLogout(ActionEvent actionEvent) throws IOException {
        try {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();

            Scene scene = new Scene(
                    FXMLLoader.load(getClass().getResource(
                            "/edu/ijse/fx/layered/orm/login.fxml"
                    ))
            );

            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();
            new Alert(Alert.AlertType.INFORMATION,"Logout Successfully !").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
