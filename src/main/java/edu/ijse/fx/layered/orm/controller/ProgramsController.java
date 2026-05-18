package edu.ijse.fx.layered.orm.controller;

import edu.ijse.fx.layered.orm.bo.BOFactory;
import edu.ijse.fx.layered.orm.bo.custom.ProgramBO;
import edu.ijse.fx.layered.orm.dto.ProgramDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProgramsController {

    private final ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAM);

    @FXML
    private TableColumn<ProgramDTO, Double> colCost;

    @FXML
    private TableColumn<ProgramDTO, String> colDuration;

    @FXML
    private TableColumn<ProgramDTO, String> colId;

    @FXML
    private TableColumn<ProgramDTO, String> colName;

    @FXML
    private TextField costTxt;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField durationTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField programTxt;

    @FXML
    private Button resetBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TableView<ProgramDTO> programTable;

    @FXML
    private Button updateBtn;

    @FXML
    void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        loadTable();

        programTable.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 1){
                loadSelectedRow();
            }
        });

    }

    void loadTable(){
        try {
            programTable.getItems().clear();
            programTable.getItems().addAll(programBO.getAll());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void loadSelectedRow(){

        ProgramDTO programDTO = programTable.getSelectionModel().getSelectedItem();

        if(programDTO != null){
            idTxt.setText(String.valueOf(programDTO.getId()));
            programTxt.setText(programDTO.getName());
            durationTxt.setText(programDTO.getDuration());
            costTxt.setText(String.valueOf(programDTO.getCost()));
        }

    }

    @FXML
    void navigateDelete(ActionEvent event) {
        try {
            boolean deleted = programBO.delete(idTxt.getText());
            if(deleted){
                loadTable();
                navigateReset(event);
                new Alert(Alert.AlertType.INFORMATION,"Program Deleted Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Delete Program").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateReset(ActionEvent event) {
        idTxt.setText("");
        programTxt.setText("");
        durationTxt.setText("");
        costTxt.setText("");
    }

    @FXML
    void navigateSave(ActionEvent event) {
        try {
            ProgramDTO programDTO = new ProgramDTO(
                    idTxt.getText(),
                    programTxt.getText(),
                    durationTxt.getText(),
                    Double.parseDouble(costTxt.getText())
            );
            boolean isSaved = programBO.save(programDTO);
            if(isSaved){
                loadTable();
                navigateReset(event);
                new Alert(Alert.AlertType.INFORMATION,"Program Save Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Save Program").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void navigateUpdate(ActionEvent event) {
        try {
            ProgramDTO programDTO = new ProgramDTO(
                    idTxt.getText(),
                    programTxt.getText(),
                    durationTxt.getText(),
                    Double.parseDouble(costTxt.getText())
            );
            boolean isUpdated = programBO.update(programDTO);
            if(isUpdated){
                loadTable();
                navigateReset(event);
                new Alert(Alert.AlertType.INFORMATION,"Program Updated Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR,"Failed to Update Program").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
