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
    private TableColumn<TherapistDTO, Integer> colId;

    @FXML
    private TableColumn<TherapistDTO, String> colProgramName;

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
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
            colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
            colSpecs.setCellValueFactory(new PropertyValueFactory<>("specialization"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

            loadTable();
            loadCustomers();

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

    void loadCustomers(){
        try {
            ArrayList <ProgramDTO> programDTOS = programBO.getAll();

            programSelect.getItems().clear();
            programSelect.setValue("Select Program");
            if (programDTOS != null){
                for (ProgramDTO programDTO : programDTOS){
                    programSelect.getItems().addAll(programDTO.getName());
                }
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void selectTherapist(){
        TherapistDTO therapistDTO = therapistTable.getSelectionModel().getSelectedItem();
        if (therapistDTO != null){
            idTxt.setText(String.valueOf(therapistDTO.getId()));
            therapistTxt.setText(therapistDTO.getTherapistName());
            programSelect.setValue(therapistDTO.getProgramName());
            specsTxt.setText(therapistDTO.getSpecialization());
            contactTxt.setText(String.valueOf(therapistDTO.getContactNo()));
        }
    }

    @FXML
    void navigateDelete(ActionEvent event) {
        try {
            boolean isDeleted = therapistBO.delete(Integer.parseInt(idTxt.getText()));

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
        try {
            TherapistDTO therapistDTO = new TherapistDTO(
                    0,
                    therapistTxt.getText(),
                    programSelect.getValue(),
                    specsTxt.getText(),
                    Integer.parseInt(contactTxt.getText())
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
        try {
            TherapistDTO therapistDTO = new TherapistDTO(
                    Integer.parseInt(idTxt.getText()),
                    therapistTxt.getText(),
                    programSelect.getValue(),
                    specsTxt.getText(),
                    Integer.parseInt(contactTxt.getText())
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
