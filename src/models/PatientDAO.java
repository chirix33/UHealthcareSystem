package models;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class PatientDAO {
    private DBConnect db = new DBConnect();

    /**
     * Hashes the input string using SHA-256 and returns the hex-encoded result.
     */
    private String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * Fetch all patients from the DB.
     */
    public List<Patient> fetchAll() throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM uhis_patients";
        try (Connection c = db.connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Patient p = new Patient(
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("last_name"),
                    rs.getDate("date_of_birth").toLocalDate(),
                    rs.getString("gender"),
                    rs.getString("nationality"),
                    rs.getString("phone_number"),
                    rs.getString("email_address"),
                    rs.getString("city"),
                    rs.getString("country"),
                    rs.getString("blood_type"),
                    rs.getString("allergies"),
                    rs.getString("chronic_conditions"),
                    rs.getString("current_medications"),
                    "", // do not expose password
                    rs.getString("emergency_phone")
                );
                list.add(p);
            }
        }
        return list;
    }

    /**
     * Add a new patient; hashes the password before storing.
     */
    public void add(Patient p) throws SQLException {
        String sql = "INSERT INTO uhis_patients ("
                   + "first_name,middle_name,last_name,date_of_birth,gender,nationality,"
                   + "phone_number,email_address,city,country,blood_type,allergies,"
                   + "chronic_conditions,current_medications,password,emergency_phone)"
                   + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getMiddleName());
            ps.setString(3, p.getLastName());
            ps.setDate(4, Date.valueOf(p.getDateOfBirth()));
            ps.setString(5, p.getGender());
            ps.setString(6, p.getNationality());
            ps.setString(7, p.getPhone());
            ps.setString(8, p.getEmail());
            ps.setString(9, p.getCity());
            ps.setString(10, p.getCountry());
            ps.setString(11, p.getBloodType());
            ps.setString(12, p.getAllergies());
            ps.setString(13, p.getChronic());
            ps.setString(14, p.getMedications());
            // Hash password
            ps.setString(15, sha256(p.getPassword()));
            ps.setString(16, p.getEmergencyPhone());
            ps.executeUpdate();
        }
    }

    /**
     * Update only the contact fields (phone and emergency phone).
     */
    public void updateContact(String email, String newPhone, String newEmergency) throws SQLException {
        String sql = "UPDATE uhis_patients SET phone_number=?, emergency_phone=? WHERE email_address=?";
        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newPhone);
            ps.setString(2, newEmergency);
            ps.setString(3, email);
            ps.executeUpdate();
        }
    }

    /**
     * Update password only; stores the SHA-256 hash.
     */
    public void updatePassword(String email, String newPwd) throws SQLException {
        String sql = "UPDATE uhis_patients SET password=? WHERE email_address=?";
        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, sha256(newPwd));
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

    /**
     * Remove a patient record by email.
     */
    public void delete(String email) throws SQLException {
        String sql = "DELETE FROM uhis_patients WHERE email_address=?";
        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }
}
