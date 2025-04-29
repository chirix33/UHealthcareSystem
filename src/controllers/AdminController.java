package controllers;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

import models.Patient;
import models.PatientDAO;

public class AdminController {
    @FXML private TableView<Patient> table;
    @FXML private TableColumn<Patient,String> colEmail;
    @FXML private TableColumn<Patient,String> colName;
    @FXML private TableColumn<Patient,String> colPhone;
    @FXML private TableColumn<Patient,String> colEmergency;
    // add other TableColumn<Patient,?> fields here if you need more columns

    private PatientDAO dao = new PatientDAO();

    @FXML
    public void initialize() {
        // Bind each column to the Patient property:
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(cell -> 
            new SimpleStringProperty(
                cell.getValue().getFirstName() + " " + cell.getValue().getLastName()
            )
        );
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmergency.setCellValueFactory(new PropertyValueFactory<>("emergencyPhone"));
        // (repeat for any additional columns)

        refreshTable();
    }

    private void refreshTable() {
        try {
            ObservableList<Patient> all = 
                FXCollections.observableArrayList(dao.fetchAll());
            table.setItems(all);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onAdd(ActionEvent ev) throws Exception {
        // opens a modal to add a new patient
        SceneLoader.loadModal("/resources/views/AddPatientView.fxml", "Add Patient");
        refreshTable();
    }

    @FXML
    protected void onEdit(ActionEvent ev) throws Exception {
        Patient sel = table.getSelectionModel().getSelectedItem();
        if (sel != null) {
            // pass selected patient into the modal
            PatientController.setEditingPatient(sel);
            SceneLoader.loadModal("/resources/views/EditPatientView.fxml", "Edit Patient");
            refreshTable();
        }
    }

    @FXML
    protected void onDelete(ActionEvent ev) {
        Patient sel = table.getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                dao.delete(sel.getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            refreshTable();
        }
    }
    
    @FXML
    protected void onLogout(ActionEvent event) {
    	PatientController.setLoggedInEmail(null);
        controllers.SceneLoader.load("/resources/views/LoginView.fxml", "Login");
    }
}