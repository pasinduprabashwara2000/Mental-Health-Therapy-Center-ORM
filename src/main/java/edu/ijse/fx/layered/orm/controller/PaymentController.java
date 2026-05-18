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

import java.util.ArrayList;
import java.util.Date;

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
