package models;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

import models.User;

public class UserDAO {
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
            return sb.toString().trim();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * Attempts to log in an admin by email and password.
     * The provided password is hashed before checking against the DB.
     */
    public User loginAdmin(String email, String pwd) throws SQLException {
        String hashedPwd = sha256(pwd);
        String sql = "SELECT email, first_name, last_name FROM uhis_admin " +
                     "WHERE email = ? AND password = ?";
        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, hashedPwd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getString("email"),
                        hashedPwd,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        true
                    );
                }
            }
        }
        return null;
    }

    /**
     * Attempts to log in a patient by email and password.
     * The provided password is hashed before checking against the DB.
     */
    public User loginPatient(String email, String pwd) throws SQLException {
        String hashedPwd = sha256(pwd);
        String sql = "SELECT first_name, middle_name, last_name FROM uhis_patients " +
                     "WHERE email_address = ? AND password = ?";
        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, hashedPwd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String fn = rs.getString("first_name");
                    String mn = rs.getString("middle_name");
                    String ln = rs.getString("last_name");
                    String fullName = fn + (mn != null && !mn.isEmpty() ? " " + mn : "") + " " + ln;
                    return new User(
                        email,
                        hashedPwd,
                        fullName,
                        "",
                        false
                    );
                }
            }
        }
        return null;
    }
}
