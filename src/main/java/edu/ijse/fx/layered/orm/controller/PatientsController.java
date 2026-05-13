package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.PatientBO;
import edu.ijse.fx.layered.orm.dto.PatientDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientsController {

    private final PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PATIENT);

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField ageTxt;

    @FXML
    private TableColumn<PatientDTO, String> colAddress;

    @FXML
    private TableColumn<PatientDTO, Integer> colAge;

    @FXML
    private TableColumn<PatientDTO, Integer> colContact;

    @FXML
    private TableColumn<PatientDTO, String> colDisease;

    @FXML
    private TableColumn<PatientDTO, String> colGender;

    @FXML
    private TableColumn<PatientDTO, Integer> colId;

    @FXML
    private TableColumn<PatientDTO, String> colName;

    @FXML
    private TextField contactTxt;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField diseaseTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private TextField patientNameTxt;

    @FXML
    private TableView<PatientDTO> patientTable;

    @FXML
    private Button resetBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDisease.setCellValueFactory(new PropertyValueFactory<>("disease"));

        loadTable();

        patientTable.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 1){
                loadRowData();
            }
        });

        genderChoiceBox.setValue("Select Gender");
        genderChoiceBox.getItems().addAll(
                "Male", "Female"
        );
    }

    void loadTable(){
        try {
           patientTable.getItems().clear();
           patientTable.getItems().addAll(patientBO.getAll());
        } catch (Exception e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void loadRowData(){
        try {
            PatientDTO patientDTO = patientTable.getSelectionModel().getSelectedItem();

            if(patientDTO != null){
                idTxt.setText(patientDTO.getPatientId());
                patientNameTxt.setText(patientDTO.getName());
                ageTxt.setText(String.valueOf(patientDTO.getAge()));
                genderChoiceBox.setValue(patientDTO.getGender());
                contactTxt.setText(patientDTO.getContactNumber());
                addressTxt.setText(patientDTO.getAddress());
                diseaseTxt.setText(patientDTO.getDisease());
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateDelete(ActionEvent event) {
        try {
            boolean isDeleted = patientBO.delete(idTxt.getText());
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Patient Deleted Successfully").show();
                loadTable();
                navigateReset(event);
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Save Patient").show();
            }
        } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateReset(ActionEvent event) {
        idTxt.setText("");
        patientNameTxt.setText("");
        ageTxt.setText("");
        genderChoiceBox.setValue(null);
        contactTxt.setText("");
        addressTxt.setText("");
        diseaseTxt.setText("");
    }

    @FXML
    void navigateSave(ActionEvent event) {
        try {
            PatientDTO patientDTO = new PatientDTO(
                idTxt.getText(),
                patientNameTxt.getText(),
                Integer.parseInt(ageTxt.getText()),
                genderChoiceBox.getValue(),
                contactTxt.getText(),
                addressTxt.getText(),
                diseaseTxt.getText(),
                null
            );
            boolean isSaved = patientBO.save(patientDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Patient Save Successfully").show();
                loadTable();
                navigateReset(event);
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Save Patient").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateUpdate(ActionEvent event) {
        try{
            PatientDTO patientDTO = new PatientDTO(
                    idTxt.getText(),
                    patientNameTxt.getText(),
                    Integer.parseInt(ageTxt.getText()),
                    genderChoiceBox.getValue(),
                    contactTxt.getText(),
                    addressTxt.getText(),
                    diseaseTxt.getText(),
                    null
            );
            boolean isUpdated = patientBO.update(patientDTO);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Patient Updated Successfully").show();
                loadTable();
                navigateReset(event);
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Update Patient").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


}
