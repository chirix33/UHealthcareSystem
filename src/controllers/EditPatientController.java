package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Patient;
import models.PatientDAO;

public class EditPatientController {
  @FXML private TextField tfFirstName, tfMiddleName, tfLastName,
                          tfEmail, tfPhone, tfEmergency, tfCountry;
  @FXML private DatePicker dpDOB;
  @FXML private ChoiceBox<String> cbGender;
  @FXML private PasswordField pfPassword;
  @FXML private Button btnUpdate;

  private PatientDAO dao = new PatientDAO();
  private Patient patient = PatientController.getEditingPatient();

  @FXML
  public void initialize() {
    // prefill all fields from 'patient'
    tfFirstName.setText(patient.getFirstName());
    tfMiddleName.setText(patient.getMiddleName());
    tfLastName.setText(patient.getLastName());
    dpDOB.setValue(patient.getDateOfBirth());
    cbGender.setValue(patient.getGender());
    tfEmail.setText(patient.getEmail());
    tfPhone.setText(patient.getPhone());
    tfEmergency.setText(patient.getEmergencyPhone());
    tfCountry.setText(patient.getCountry());
    // password left blank for security
  }

  @FXML
  protected void onUpdate() {
    try {
      patient.setFirstName(tfFirstName.getText());
      patient.setMiddleName(tfMiddleName.getText());
      patient.setLastName(tfLastName.getText());
      patient.setDateOfBirth(dpDOB.getValue());
      patient.setGender(cbGender.getValue());
      patient.setPhone(tfPhone.getText());
      patient.setEmergencyPhone(tfEmergency.getText());
      if (!pfPassword.getText().isEmpty()) {
        patient.setPassword(pfPassword.getText());
        dao.updatePassword(patient.getEmail(), pfPassword.getText());
      }
      dao.updateContact(patient.getEmail(), tfPhone.getText(), tfEmergency.getText());
      // If you want to update name or DOB in DB, add more update methods
      closeWindow();
    } catch (Exception e) {
      new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    }
  }

  private void closeWindow() {
    ((Stage) btnUpdate.getScene().getWindow()).close();
  }
}
