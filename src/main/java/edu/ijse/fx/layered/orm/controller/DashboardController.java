package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.DashboardBO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    private final DashboardBO dashboardBO = (DashboardBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.DASHBOARD);

    @FXML
    private Label programsTxt;

    @FXML
    private Label revenueTxt;

    @FXML
    private Label sessionsTxt;

    @FXML
    private Label therapistsTxt;

    @FXML
    void initialize(){
        loadTherapistCount();
        loadProgramCount();
        loadSessionCount();
        findTotalRevenue();
    }

    void loadTherapistCount(){
        try {
            int count = dashboardBO.findTherapistCount();
            therapistsTxt.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadProgramCount(){
        try {
            int count = dashboardBO.findProgramsCount();
            programsTxt.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadSessionCount(){
        try {
            int count = dashboardBO.findSessionsCount();
            sessionsTxt.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void findTotalRevenue(){
        try {
            double revenue = dashboardBO.findTotalRevenue();
            revenueTxt.setText(String.valueOf(revenue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
