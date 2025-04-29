package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.User;
import models.UserDAO;

public class LoginController {
    @FXML private TextField  txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label      lblError;

    private UserDAO userDAO = new UserDAO();

    @FXML
    protected void onLogin(ActionEvent evt) {
        String email = txtEmail.getText(),
               pwd   = txtPassword.getText();
        try {
            // try admin
            User u = userDAO.loginAdmin(email, pwd);
            if (u != null) {
                SceneLoader.load("/resources/views/AdminDashboard.fxml", "Admin Dashboard");
                return;
            }
            // try patient
            u = userDAO.loginPatient(email, pwd);
            if (u != null) {
                PatientController.setLoggedInEmail(email);
                SceneLoader.load("/resources/views/PatientDashboard.fxml", "Patient Dashboard");
                return;
            }
            lblError.setText("Invalid credentials");
        } catch (Exception ex) {
            lblError.setText("Error: " + ex.getMessage());
        }
    }
}
