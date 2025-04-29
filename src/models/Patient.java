package models;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class Patient {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String phone;
    private String email;
    private String city;
    private String country;
    private String bloodType;
    private String allergies;
    private String chronic;
    private String medications;
    private String password;
    private String emergencyPhone;

    public Patient(
            String firstName,
            String middleName,
            String lastName,
            LocalDate dateOfBirth,
            String gender,
            String nationality,
            String phone,
            String email,
            String city,
            String country,
            String bloodType,
            String allergies,
            String chronic,
            String medications,
            String password,
            String emergencyPhone
    ) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.country = country;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.chronic = chronic;
        this.medications = medications;
        this.password = password;
        this.emergencyPhone = emergencyPhone;
    }
    
    private String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().trim();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getNationality() { return nationality; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getBloodType() { return bloodType; }
    public String getAllergies() { return allergies; }
    public String getChronic() { return chronic; }
    public String getMedications() { return medications; }
    public String getPassword() { return sha256(password); }
    public String getEmergencyPhone() { return emergencyPhone; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setGender(String gender) { this.gender = gender; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setCity(String city) { this.city = city; }
    public void setCountry(String country) { this.country = country; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public void setChronic(String chronic) { this.chronic = chronic; }
    public void setMedications(String medications) { this.medications = medications; }
    public void setPassword(String password) { this.password = password; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }
}
