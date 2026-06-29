package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.DashboardBO;
import edu.ijse.fx.layered.orm.bo.custom.AdminReportBO;
import edu.ijse.fx.layered.orm.bo.custom.SessionBO;
import edu.ijse.fx.layered.orm.dto.AdminReportDTO;
import edu.ijse.fx.layered.orm.dto.SessionDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.util.ArrayList;
import java.util.Date;

public class DashboardController {

    private final DashboardBO dashboardBO = (DashboardBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.DASHBOARD);
    private final AdminReportBO reportBO = (AdminReportBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ADMINREPORT);
    private final SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SESSION);

    @FXML
    private Label programsTxt;

    @FXML
    private Label revenueTxt;

    @FXML
    private Label sessionsTxt;

    @FXML
    private Label therapistsTxt;

    @FXML
    private TableView<AdminReportDTO> tblReports;

    @FXML
    private TableColumn<AdminReportDTO, String> colTherapistId;

    @FXML
    private TableColumn<AdminReportDTO, String> colTherapistName;

    @FXML
    private TableColumn<AdminReportDTO, Integer> colSessionCount;

    @FXML
    private TableColumn<AdminReportDTO, Integer> colCompletedSessions;

    @FXML
    private TableColumn<AdminReportDTO, Integer> colCancelledSessions;

    @FXML
    private TableColumn<AdminReportDTO, String> colPerformance;

    @FXML
    private TableColumn<SessionDTO, Date> colDate;

    @FXML
    private TableColumn<SessionDTO, String> colTime;

    @FXML
    private TableColumn<SessionDTO, String> colPID;

    @FXML
    private TableColumn<SessionDTO, String> colSID;

    @FXML
    private TableColumn<SessionDTO, String> colStatus;

    @FXML
    private TableColumn<SessionDTO, String> colTID;

    @FXML
    private TableView<SessionDTO> sessionTable;

    @FXML
    void initialize() {
        colSID.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colTID.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colPID.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        setupTableColumns();
        loadTherapistCount();
        loadProgramCount();
        loadSessionCount();
        findTotalRevenue();

        loadTable();
        loadSessionTable();
    }

    private void setupTableColumns() {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colSessionCount.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));
        colCompletedSessions.setCellValueFactory(new PropertyValueFactory<>("completedSessions"));
        colCancelledSessions.setCellValueFactory(new PropertyValueFactory<>("cancelledSessions"));

        if (colPerformance != null) {
            colPerformance.setCellValueFactory(data ->
                    new ReadOnlyObjectWrapper<>(data.getValue().getPerformancePercent() + "%"));

            colPerformance.setCellFactory(col -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) { setText(null); setStyle(""); return; }
                    setText(item);
                    double val = Double.parseDouble(item.replace("%", ""));
                    if (val >= 80)      setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
                    else if (val >= 50) setStyle("-fx-text-fill: #d97706; -fx-font-weight: bold;");
                    else                setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                }
            });
        }
    }

    private void loadTable() {
        try {
            ArrayList<AdminReportDTO> reports = reportBO.getTherapistPerformance(null, null, "All");
            tblReports.setItems(FXCollections.observableArrayList(reports));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load report table: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    void loadSessionTable(){
        try {
            sessionTable.getItems().clear();
            sessionTable.getItems().addAll(sessionBO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadTherapistCount() {
        try {
            therapistsTxt.setText(String.valueOf(dashboardBO.findTherapistCount()));
        } catch (Exception e) {
            therapistsTxt.setText("—");
            e.printStackTrace();
        }
    }

    void loadProgramCount() {
        try {
            programsTxt.setText(String.valueOf(dashboardBO.findProgramsCount()));
        } catch (Exception e) {
            programsTxt.setText("—");
            e.printStackTrace();
        }
    }

    void loadSessionCount() {
        try {
            sessionsTxt.setText(String.valueOf(dashboardBO.findSessionsCount()));
        } catch (Exception e) {
            sessionsTxt.setText("—");
            e.printStackTrace();
        }
    }

    void findTotalRevenue() {
        try {
            double revenue = dashboardBO.findTotalRevenue();
            revenueTxt.setText(String.format("%.2f", revenue));
        } catch (Exception e) {
            revenueTxt.setText("—");
            e.printStackTrace();
        }
    }
}