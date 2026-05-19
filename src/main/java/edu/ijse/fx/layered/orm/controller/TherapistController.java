package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.ProgramBO;
import edu.ijse.fx.layered.orm.bo.custom.TherapistBO;
import edu.ijse.fx.layered.orm.dto.ProgramDTO;
import edu.ijse.fx.layered.orm.dto.TherapistDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class TherapistController {

    private final TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.THERAPIST);
    private final ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAM);

    @FXML
    private TableColumn<TherapistDTO, Integer> colContact;

    @FXML
    private TableColumn<TherapistDTO, String> colId;

    @FXML
    private TableColumn<TherapistDTO, String> colProgramId;

    @FXML
    private TableColumn<TherapistDTO, String> colSpecs;

    @FXML
    private TableColumn<TherapistDTO, String> colTherapistName;

    @FXML
    private TextField contactTxt;

    @FXML
    private Button deleteBtn;

    @FXML
    private ChoiceBox<String> programSelect;

    @FXML
    private TextField idTxt;

    @FXML
    private Button resetBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField specsTxt;

    @FXML
    private TableView<TherapistDTO> therapistTable;

    @FXML
    private TextField therapistTxt;

    @FXML
    private Button updateBtn;

    @FXML
    void initialize() {
            colId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
            colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
            colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
            colSpecs.setCellValueFactory(new PropertyValueFactory<>("specialization"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

            loadTable();
            loadPrograms();

            therapistTable.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 1){
                    selectTherapist();
                }
            });

    }

    void loadTable(){
        try {
            therapistTable.getItems().clear();
            therapistTable.getItems().addAll(therapistBO.getAll());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void loadPrograms(){
        try {
            ArrayList <ProgramDTO> programDTOS = programBO.getAll();

            programSelect.getItems().clear();
            programSelect.setValue("Select Program");
            if (programDTOS != null){
                for (ProgramDTO programDTO : programDTOS){
                    programSelect.getItems().addAll(String.valueOf(programDTO.getId()));
                }
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void selectTherapist(){
        TherapistDTO therapistDTO = therapistTable.getSelectionModel().getSelectedItem();
        if (therapistDTO != null){
            idTxt.setText(therapistDTO.getTherapistId());
            therapistTxt.setText(therapistDTO.getTherapistName());
            programSelect.setValue(String.valueOf(therapistDTO.getProgramId()));
            specsTxt.setText(therapistDTO.getSpecialization());
            contactTxt.setText(String.valueOf(therapistDTO.getContactNo()));
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(\\+94|0)(7\\d{8}|1\\d{8}|2\\d{8}|3\\d{8}|4\\d{8}|5\\d{8}|6\\d{8}|8\\d{8}|9\\d{8})$";
        return phoneNumber.matches(regex);
    }

    @FXML
    void navigateDelete(ActionEvent event) {
        try {
            boolean isDeleted = therapistBO.delete(idTxt.getText());

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION,"Therapist Deleted Successfully").show();
                loadTable();
                navigateReset(event);
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Delete Therapist").show();
            }
        } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void navigateReset(ActionEvent event) {
        idTxt.setText("");
        therapistTxt.setText("");
        programSelect.setValue(null);
        specsTxt.setText("");
        contactTxt.setText("");
    }

    @FXML
    void navigateSave(ActionEvent event) {

        String contactNo = contactTxt.getText();

        if (!isValidPhoneNumber(contactNo)){
            new Alert(Alert.AlertType.ERROR,"Invalid Contact Number").show();
            return;
        }

        try {
            TherapistDTO therapistDTO = new TherapistDTO(
                    idTxt.getText(),
                    therapistTxt.getText(),
                    programSelect.getValue(),
                    specsTxt.getText(),
                    Integer.parseInt(contactNo)
            );
            boolean isSaved = therapistBO.save(therapistDTO);

            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Therapist Save Successfully").show();
                loadTable();
                navigateReset(event);
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Save Therapist").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateUpdate(ActionEvent event) {
        String contactNo = contactTxt.getText();

        if(!isValidPhoneNumber(contactNo)){
            new Alert(Alert.AlertType.ERROR,"Invalid Contact Number").show();
            return;
        }

        try {
            TherapistDTO therapistDTO = new TherapistDTO(
                    idTxt.getText(),
                    therapistTxt.getText(),
                    programSelect.getValue(),
                    specsTxt.getText(),
                    Integer.parseInt(contactNo)
            );
            boolean isUpdated = therapistBO.update(therapistDTO);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Therapist Updated Successfully").show();
                loadTable();
                navigateReset(event);
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Update Therapist").show();
            }
        } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
