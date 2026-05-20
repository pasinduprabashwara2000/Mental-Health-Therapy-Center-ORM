package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.PaymentBO;
import edu.ijse.fx.layered.orm.bo.custom.SessionBO;
import edu.ijse.fx.layered.orm.dto.PaymentDTO;
import edu.ijse.fx.layered.orm.dto.SessionDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentController {

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);
    private final SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SESSION);

    @FXML
    private TextField amountTxt;

    @FXML
    private TableColumn<PaymentDTO, Double> colAmount;

    @FXML
    private TableColumn<PaymentDTO, Date> colPaymentDate;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentId;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentMethod;

    @FXML
    private TableColumn<PaymentDTO, String> colSessionId;

    @FXML
    private TableColumn<PaymentDTO, String> colStatus;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField idTxt;

    @FXML
    private TableView<PaymentDTO> paymentTable;

    @FXML
    private ComboBox<String> paymentMethodPicker;

    @FXML
    private Button resetBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<String> sessionIdPicker;

    @FXML
    private ComboBox<String> statusPicker;

    @FXML
    private Button updateBtn;

    @FXML
    private Button printBtn;

    @FXML
    void initialize(){
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        paymentMethodPicker.getItems().addAll("Cash",
                "Card",
                "Credit Card",
                "Debit Card",
                "Online Transfer",
                "Bank Transfer",
                "Mobile Payment",
                "PayPal",
                "Cheque",
                "Insurance");

        statusPicker.getItems().addAll("Paid",
                "Pending",
                "Cancelled",
                "Failed",
                "Refunded",
                "Partially Paid",
                "Overdue");

        loadSessionId();
        loadTable();

        paymentTable.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 1){
                loadSelectedRow();
            }
        });

    }

    void loadSessionId(){

        try {
            ArrayList<SessionDTO> sessionDTOS = sessionBO.getAll();
            sessionIdPicker.getItems().clear();

            if(sessionDTOS != null){
                for (SessionDTO sessionDTO : sessionDTOS){
                    sessionIdPicker.getItems().addAll(sessionDTO.getSessionId());
                }
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void loadTable(){
        try {
            paymentTable.getItems().clear();
            paymentTable.getItems().addAll(paymentBO.getAll());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void loadSelectedRow(){
        PaymentDTO paymentDTO = paymentTable.getSelectionModel().getSelectedItem();

        if (paymentDTO != null){
            idTxt.setText(paymentDTO.getPaymentId());
            sessionIdPicker.setValue(paymentDTO.getSessionId());
            amountTxt.setText(String.valueOf(paymentDTO.getAmount()));
            paymentMethodPicker.setValue(paymentDTO.getPaymentMethod());
            datePicker.setValue(paymentDTO.getPaymentDate());
            statusPicker.setValue(paymentDTO.getStatus());
        }
    }

    @FXML
    void navigatePrint(ActionEvent event) {
        PaymentDTO selected = paymentTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment to print.").show();
            return;
        }

        try {
            // Use absolute path directly — no classpath lookup
            String reportPath = "src/main/resources/edu/ijse/fx/layered/orm/reports/invoice.jrxml";

            File reportFile = new File(reportPath);

            if (!reportFile.exists()) {
                new Alert(Alert.AlertType.ERROR,
                        "Report file not found at: " + reportFile.getAbsolutePath()).show();
                return;
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(
                    reportFile.getAbsolutePath());

            Map<String, Object> params = new HashMap<>();
            params.put("paymentId",     selected.getPaymentId());
            params.put("sessionId",     selected.getSessionId());
            params.put("amount",        selected.getAmount());
            params.put("paymentMethod", selected.getPaymentMethod());
            params.put("paymentDate",   selected.getPaymentDate() != null ?
                    selected.getPaymentDate().toString() : "N/A");
            params.put("status",        selected.getStatus());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, params, new JREmptyDataSource());

            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate invoice: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void navigateDelete(ActionEvent event) {
        try {
            boolean isDeleted = paymentBO.delete(idTxt.getText());
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION,"Payment Deleted Successfully").show();
                navigateReset(event);
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Delete Payment").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateReset(ActionEvent event) {
        idTxt.setText("");
        sessionIdPicker.setValue(null);
        amountTxt.setText("");
        paymentMethodPicker.setValue(null);
        datePicker.setValue(null);
        statusPicker.setValue(null);
    }

    @FXML
    void navigateSave(ActionEvent event) {
        try {
            PaymentDTO paymentDTO = new PaymentDTO(
                idTxt.getText(),
                sessionIdPicker.getValue(),
                Double.parseDouble(amountTxt.getText()),
                paymentMethodPicker.getValue(),
                datePicker.getValue(),
                statusPicker.getValue()
            );
            boolean isSaved = paymentBO.save(paymentDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Payment Save Successfully").show();
                navigateReset(event);
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Save Payment").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateUpdate(ActionEvent event) {
        try {
            PaymentDTO paymentDTO = new PaymentDTO(
                    idTxt.getText(),
                    sessionIdPicker.getValue(),
                    Double.parseDouble(amountTxt.getText()),
                    paymentMethodPicker.getValue(),
                    datePicker.getValue(),
                    statusPicker.getValue()
            );
            boolean isUpdated = paymentBO.update(paymentDTO);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Payment Update Successfully").show();
                navigateReset(event);
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Update Payment").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
