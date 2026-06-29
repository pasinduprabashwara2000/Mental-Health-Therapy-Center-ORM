package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.AdminReportBO;
import edu.ijse.fx.layered.orm.dto.AdminReportDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminReportController {

    private final AdminReportBO reportBO = (AdminReportBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ADMINREPORT);

    @FXML private BarChart<String, Number> barTherapistPerformance;
    @FXML private CategoryAxis barXAxis;
    @FXML private NumberAxis barYAxis;
    @FXML private PieChart pieSessionStats;
    @FXML private DatePicker txtFromDate;
    @FXML private DatePicker txtToDate;
    @FXML private ComboBox<String> cmbTherapist;
    @FXML private ComboBox<String> cmbSessionType;
    @FXML private Button btnGenerate;
    @FXML private Button btnClear;
    @FXML private Label lblTotalSessions;
    @FXML private Label lblCompletedSessions;
    @FXML private Label lblCancelledSessions;
    @FXML private Label lblAvgPerformance;
    @FXML private TableView<AdminReportDTO> tblReports;
    @FXML private TableColumn<AdminReportDTO, String> colTherapistId;
    @FXML private TableColumn<AdminReportDTO, String> colTherapistName;
    @FXML private TableColumn<AdminReportDTO, Integer> colSessionCount;
    @FXML private TableColumn<AdminReportDTO, Integer> colCompletedSessions;
    @FXML private TableColumn<AdminReportDTO, Integer> colCancelledSessions;
    @FXML private TableColumn<AdminReportDTO, String> colPerformance;

    private ArrayList<AdminReportDTO> currentReports = new ArrayList<>();

    @FXML
    void initialize() {
        setupTableColumns();
        loadTherapistCombo();
        cmbSessionType.setItems(FXCollections.observableArrayList("All", "Completed", "Pending", "Cancelled"));
        cmbSessionType.getSelectionModel().selectFirst();
        generateReport();
    }

    private void setupTableColumns() {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colSessionCount.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));
        colCompletedSessions.setCellValueFactory(new PropertyValueFactory<>("completedSessions"));
        colCancelledSessions.setCellValueFactory(new PropertyValueFactory<>("cancelledSessions"));
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

    private void loadTherapistCombo() {
        try {
            ArrayList<String> ids = reportBO.getAllTherapistIds();
            ids.add(0, "All");
            cmbTherapist.setItems(FXCollections.observableArrayList(ids));
            cmbTherapist.getSelectionModel().selectFirst();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error loading therapists: " + e.getMessage());
        }
    }

    @FXML
    void btnGenerateOnAction(ActionEvent event) {
        generateReport();
        generateJasperReport();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtFromDate.setValue(null);
        txtToDate.setValue(null);
        cmbTherapist.getSelectionModel().selectFirst();
        cmbSessionType.getSelectionModel().selectFirst();
        generateReport();
    }

    private void generateReport() {
        LocalDate from       = txtFromDate.getValue();
        LocalDate to         = txtToDate.getValue();
        String    therapistId = cmbTherapist.getValue();

        if (from != null && to != null && from.isAfter(to)) {
            showAlert(Alert.AlertType.WARNING, "'From Date' must be before 'To Date'.");
            return;
        }

        try {
            currentReports = reportBO.getTherapistPerformance(from, to, therapistId);
            tblReports.setItems(FXCollections.observableArrayList(currentReports));
            loadBarChart(currentReports);
            updateSummaryCards(currentReports);

            Map<String, Long> statusStats = reportBO.getSessionStatusStats(from, to);
            loadPieChart(statusStats);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void generateJasperReport() {
        try {
            int total = 0, completed = 0, cancelled = 0;
            double totalPerf = 0;
            for (AdminReportDTO r : currentReports) {
                total     += r.getTotalSessions();
                completed += r.getCompletedSessions();
                cancelled += r.getCancelledSessions();
                totalPerf += r.getPerformancePercent();
            }
            double avgPerf = currentReports.isEmpty() ? 0
                    : Math.round(totalPerf / currentReports.size() * 10.0) / 10.0;

            Map<String, Object> params = new HashMap<>();
            params.put("fromDate",       txtFromDate.getValue() != null ? txtFromDate.getValue().toString() : "All");
            params.put("toDate",         txtToDate.getValue()   != null ? txtToDate.getValue().toString()   : "All");
            params.put("generatedBy",    "Admin");
            params.put("totalSessions",  total);
            params.put("completedCount", completed);
            params.put("cancelledCount", cancelled);
            params.put("avgPerformance", avgPerf + "%");

            InputStream jrxmlStream = getClass().getResourceAsStream(
                    "/edu/ijse/fx/layered/orm/reports/therapist_performance_report.jrxml");
            if (jrxmlStream == null) {
                showAlert(Alert.AlertType.ERROR, "Report template not found.");
                return;
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(currentReports);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            String fileName = "Therapist_Report_" +
                    new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".pdf";
            File outputFile = new File(System.getProperty("java.io.tmpdir"), fileName);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
            exporter.exportReport();

            showAlert(Alert.AlertType.INFORMATION,
                    "Report generated successfully!\nSaved to: " + outputFile.getAbsolutePath());

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(outputFile.toURI());
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Failed to generate PDF report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadBarChart(ArrayList<AdminReportDTO> reports) {
        barTherapistPerformance.getData().clear();

        XYChart.Series<String, Number> completedSeries = new XYChart.Series<>();
        completedSeries.setName("Completed");
        XYChart.Series<String, Number> cancelledSeries = new XYChart.Series<>();
        cancelledSeries.setName("Cancelled");

        for (AdminReportDTO r : reports) {
            completedSeries.getData().add(new XYChart.Data<>(r.getTherapistId(), r.getCompletedSessions()));
            cancelledSeries.getData().add(new XYChart.Data<>(r.getTherapistId(), r.getCancelledSessions()));
        }
        barTherapistPerformance.getData().addAll(completedSeries, cancelledSeries);

        for (XYChart.Data<String, Number> d : completedSeries.getData())
            if (d.getNode() != null) d.getNode().setStyle("-fx-bar-fill: #22c55e;");
        for (XYChart.Data<String, Number> d : cancelledSeries.getData())
            if (d.getNode() != null) d.getNode().setStyle("-fx-bar-fill: #ef4444;");
    }

    private void loadPieChart(Map<String, Long> stats) {
        pieSessionStats.getData().clear();
        String[] colors = {"#3b82f6", "#22c55e", "#f59e0b", "#ef4444", "#8b5cf6"};
        int i = 0;
        for (Map.Entry<String, Long> entry : stats.entrySet()) {
            PieChart.Data slice = new PieChart.Data(
                    entry.getKey() + " (" + entry.getValue() + ")", entry.getValue());
            pieSessionStats.getData().add(slice);
            final String color = colors[i++ % colors.length];
            slice.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) newNode.setStyle("-fx-pie-color: " + color + ";");
            });
        }
    }

    private void updateSummaryCards(ArrayList<AdminReportDTO> reports) {
        int total = 0, completed = 0, cancelled = 0;
        double totalPerf = 0;
        for (AdminReportDTO r : reports) {
            total     += r.getTotalSessions();
            completed += r.getCompletedSessions();
            cancelled += r.getCancelledSessions();
            totalPerf += r.getPerformancePercent();
        }
        double avgPerf = reports.isEmpty() ? 0 : Math.round(totalPerf / reports.size() * 10.0) / 10.0;

        if (lblTotalSessions     != null) lblTotalSessions.setText(String.valueOf(total));
        if (lblCompletedSessions != null) lblCompletedSessions.setText(String.valueOf(completed));
        if (lblCancelledSessions != null) lblCancelledSessions.setText(String.valueOf(cancelled));
        if (lblAvgPerformance    != null) lblAvgPerformance.setText(avgPerf + "%");
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Report");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}