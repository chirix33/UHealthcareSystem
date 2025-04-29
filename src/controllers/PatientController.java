package controllers;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Patient;
import models.PatientDAO;

public class PatientController {
    private static String loggedInEmail;
    private static Patient editingPatient;

    public static void setLoggedInEmail(String e) {
        loggedInEmail = e;
    }

    public static void setEditingPatient(Patient p) {
        editingPatient = p;
    }

    public static Patient getEditingPatient() {
        return editingPatient;
    }

    @FXML private Label lblName;
    @FXML private Label lblDOB;
    @FXML private Label lblGender;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmergency;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtCPassword;
    @FXML private Button btnUpdate;

    private PatientDAO dao = new PatientDAO();

    @FXML
    public void initialize() {
        try {
            Patient p = dao.fetchAll()
                    .stream()
                    .filter(x -> x.getEmail().equals(loggedInEmail))
                    .findFirst()
                    .orElse(null);
            if (p != null) {
                lblName.setText(p.getFirstName() + " " + p.getLastName());
                lblDOB.setText(p.getDateOfBirth().toString());
                lblGender.setText(p.getGender());
                txtPhone.setText(p.getPhone());
                txtEmergency.setText(p.getEmergencyPhone());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading profile: " + e.getMessage()).show();
        }
    }

    @FXML
    protected void onUpdate(ActionEvent ev) {
        try {
            String newPwd = txtPassword.getText();
            String newCPwd = txtCPassword.getText();
            if (newPwd != null && !newPwd.isEmpty()) {
            	if (newPwd == newCPwd) {
            		dao.updatePassword(loggedInEmail, newPwd);
            		new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();
            	} else {
            		new Alert(Alert.AlertType.WARNING, "Both passwords are not the same").show();
            	}
            }
            
            dao.updateContact(loggedInEmail,
                              txtPhone.getText(),
                              txtEmergency.getText());
            new Alert(Alert.AlertType.INFORMATION, "Contact Profile updated successfully!").show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Update failed: " + e.getMessage()).show();
        }
    }
    
    @FXML
    protected void onLogout(ActionEvent event) {
    	PatientController.setLoggedInEmail(null);
        controllers.SceneLoader.load("/resources/views/LoginView.fxml", "Login");
    }


}
