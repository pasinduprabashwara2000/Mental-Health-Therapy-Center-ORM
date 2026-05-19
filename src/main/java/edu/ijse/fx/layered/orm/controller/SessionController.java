package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.PatientBO;
import edu.ijse.fx.layered.orm.bo.custom.SessionBO;
import edu.ijse.fx.layered.orm.bo.custom.TherapistBO;
import edu.ijse.fx.layered.orm.dto.PatientDTO;
import edu.ijse.fx.layered.orm.dto.SessionDTO;
import edu.ijse.fx.layered.orm.dto.TherapistDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.Date;

public class SessionController {

    private final SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SESSION);
    private final TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.THERAPIST);
    private final PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PATIENT);

    @FXML
    private TableColumn<SessionDTO, Date> colDate;

    @FXML
    private TableColumn<SessionDTO, String> colPatientId;

    @FXML
    private TableColumn<SessionDTO, String> colSessionId;

    @FXML
    private TableColumn<SessionDTO, String> colTherapistId;

    @FXML
    private TableColumn<SessionDTO, String> colTime;

    @FXML
    private TableColumn<SessionDTO, String> colStatus;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField timeTxt;

    @FXML
    private ComboBox<String> patientIdPicker;

    @FXML
    private ComboBox<String> statusPicker;

    @FXML
    private Button resetBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TableView<SessionDTO> sessionTable;

    @FXML
    private ComboBox<String> therapistIdPicker;

    @FXML
    private Button updateBtn;

    @FXML
    void initialize(){

        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        sessionTable.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 1){
                loadSelectedRow();
            }
        });

        loadTherapist();
        loadPatient();
        loadTable();

        statusPicker.getItems().addAll(
                "Pending",
                "Completed",
                "Cancelled",
                "Rescheduled"
        );

    }

    void loadTherapist() {
        try {
            ArrayList<TherapistDTO> therapistDTOS = therapistBO.getAll();
            therapistIdPicker.getItems().clear();
            if(therapistDTOS != null){
                for (TherapistDTO therapistDTO : therapistDTOS){
                    therapistIdPicker.getItems().add(therapistDTO.getTherapistId());
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void loadPatient() {
        try {
            ArrayList<PatientDTO> patientDTOS = patientBO.getAll();
            patientIdPicker.getItems().clear();
            if(patientDTOS!=null){
                for (PatientDTO patientDTO : patientDTOS){
                    patientIdPicker.getItems().add(patientDTO.getPatientId());
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    void loadTable(){
        try {
            sessionTable.getItems().clear();
            sessionTable.getItems().addAll(sessionBO.getAll());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void loadSelectedRow(){
        SessionDTO sessionDTO = sessionTable.getSelectionModel().getSelectedItem();

        if(sessionDTO != null){
            idTxt.setText(sessionDTO.getSessionId());
            therapistIdPicker.setValue(sessionDTO.getTherapistId());
            patientIdPicker.setValue(sessionDTO.getPatientId());
            datePicker.setValue(sessionDTO.getDate());
            timeTxt.setText(sessionDTO.getTime());
            statusPicker.setValue(sessionDTO.getStatus());
        }
    }

    @FXML
    void navigateDelete(ActionEvent event) {
        try {
            boolean isDeleted = sessionBO.delete(idTxt.getText());
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Session Delete Successfully").show();
                navigateReset(event);
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Delete Session").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateReset(ActionEvent event) {
        idTxt.setText("");
        therapistIdPicker.setValue(null);
        patientIdPicker.setValue(null);
        datePicker.setValue(null);
        timeTxt.setText("");
        statusPicker.setValue(null);
    }

    @FXML
    void navigateSave(ActionEvent event) {
        try {
            SessionDTO sessionDTO = new SessionDTO(
                    idTxt.getText(),
                    therapistIdPicker.getValue(),
                    patientIdPicker.getValue(),
                    datePicker.getValue(),
                    timeTxt.getText(),
                    statusPicker.getValue()

            );
            boolean isSaved = sessionBO.save(sessionDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Session Save Successfully").show();
                navigateReset(event);
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Save Session").show();
            }
        } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateUpdate(ActionEvent event) {
        try {
            SessionDTO sessionDTO = new SessionDTO(
                    idTxt.getText(),
                    therapistIdPicker.getValue(),
                    patientIdPicker.getValue(),
                    datePicker.getValue(),
                    timeTxt.getText(),
                    statusPicker.getValue()
            );
            boolean isUpdated = sessionBO.update(sessionDTO);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Session Update Successfully").show();
                navigateReset(event);
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Update Session").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
