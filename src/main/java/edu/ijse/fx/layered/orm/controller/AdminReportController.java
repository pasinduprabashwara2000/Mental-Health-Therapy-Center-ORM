package edu.ijse.fx.layered.orm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminReportController {

    @FXML
    private BarChart<?, ?> barTherapistPerformance;

    @FXML
    private Button btnGenerate;

    @FXML
    private ComboBox<?> cmbSessionType;

    @FXML
    private ComboBox<?> cmbTherapist;

    @FXML
    private TableColumn<?, ?> colCancelledSessions;

    @FXML
    private TableColumn<?, ?> colCompletedSessions;

    @FXML
    private TableColumn<?, ?> colPerformance;

    @FXML
    private TableColumn<?, ?> colSessionCount;

    @FXML
    private TableColumn<?, ?> colTherapistId;

    @FXML
    private TableColumn<?, ?> colTherapistName;

    @FXML
    private PieChart pieSessionStats;

    @FXML
    private TableView<?> tblReports;

    @FXML
    private DatePicker txtFromDate;

    @FXML
    private DatePicker txtToDate;

    @FXML
    void btnGenerateOnAction(ActionEvent event) {

    }

}
