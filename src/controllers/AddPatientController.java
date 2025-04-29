package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Patient;
import models.PatientDAO;

public class AddPatientController {
    @FXML private TextField    tfFirstName, tfMiddleName, tfLastName;
    @FXML private DatePicker   dpDOB;
    @FXML private ChoiceBox<String> cbGender;

    @FXML private TextField    tfNationality, tfCity, tfCountry;
    @FXML private TextField    tfPhone, tfEmergencyPhone;

    @FXML private ChoiceBox<String> cbBloodType;
    @FXML private TextArea     taAllergies, taChronic, taMedications;

    @FXML private TextField    tfEmail;
    @FXML private PasswordField pfPassword;

    @FXML private Button       onSave;

    private final PatientDAO dao = new PatientDAO();

    @FXML
    protected void onSave() {
        try {
            Patient p = new Patient(
                tfFirstName.getText(),
                tfMiddleName.getText(),
                tfLastName.getText(),
                dpDOB.getValue(),
                cbGender.getValue(),
                tfNationality.getText(),
                tfPhone.getText(),
                tfEmail.getText(),
                tfCity.getText(),
                tfCountry.getText(),
                cbBloodType.getValue(),
                taAllergies.getText(),
                taChronic.getText(),
                taMedications.getText(),
                pfPassword.getText(),
                tfEmergencyPhone.getText()
            );

            dao.add(p);
            closeWindow();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to add patient:\n" + e.getMessage())
                .showAndWait();
        }
    }

    private void closeWindow() {
        ((Stage) onSave.getScene().getWindow()).close();
    }
}
