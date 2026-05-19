package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.LoginBO;
import edu.ijse.fx.layered.orm.dto.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private final LoginBO loginBO = (LoginBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.LOGIN);

    @FXML
    private Button clearBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void clearFields(ActionEvent event) {
        txtUsername.clear();
        txtPassword.clear();
    }

    @FXML
    void navigateLogin(ActionEvent event) {
        try {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            LoginDTO loginDTO = loginBO.findUsernameAndPassword(username, password);

            if (loginDTO != null) {

                Stage stage = (Stage) loginBtn.getScene().getWindow();
                Scene scene;

                if (loginDTO.getRole().equalsIgnoreCase("Admin")) {

                    scene = new Scene(
                            FXMLLoader.load(
                                    getClass().getResource(
                                            "/edu/ijse/fx/layered/orm/MainMenu.fxml"
                                    )
                            )
                    );

                    stage.setScene(scene);
                    stage.centerOnScreen();

                    new Alert(Alert.AlertType.INFORMATION, "Admin Login Successfully!").show();

                } else if (loginDTO.getRole().equalsIgnoreCase("Receptionist")) {

                    scene = new Scene(
                            FXMLLoader.load(
                                    getClass().getResource(
                                            "/edu/ijse/fx/layered/orm/MainMenu2.fxml"
                                    )
                            )
                    );
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    new Alert(Alert.AlertType.INFORMATION, "Receptionist Login Successfully!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Username or Password").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}