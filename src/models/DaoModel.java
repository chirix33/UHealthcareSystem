package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import loan_app.BankRecord;

public class DaoModel {
    // Declare DB objects
    DBConnect conn = null;
    Statement stmt = null;

    // constructor
    public DaoModel() {
        // create db connect object instance
        conn = new DBConnect();
    }

    // CREATE TABLE METHOD
    public void createTable() {
        try {
            System.out.println("Connecting to database to create table...");
            stmt = conn.connect().createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS a_abdu_tab ("
                       + " pid INTEGER NOT NULL AUTO_INCREMENT, "
                       + " id VARCHAR(10), "
                       + " income NUMERIC(8,2), "
                       + " pep VARCHAR(3), "
                       + " PRIMARY KEY (pid))";

            stmt.executeUpdate(sql);
            System.out.println("Created table a_abdu_tab in given database...");
            conn.connect().close();
        } catch (SQLException se) {
            // If the table already exists, you can handle or ignore the error
            se.printStackTrace();
        }
    }

    // INSERT INTO METHOD
    public void insertRecords(BankRecord[] robjs) {
        System.out.println("Inserting records using PreparedStatement...");
        String sql = "INSERT INTO a_abdu_tab (id, income, pep) VALUES (?, ?, ?)";

        try (Connection connection = conn.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (BankRecord record : robjs) {
                pstmt.setString(1, record.getId());
                pstmt.setDouble(2, record.getIncome());
                pstmt.setString(3, record.getPep());
                pstmt.executeUpdate();
            }
            System.out.println("Records inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public void insertRecords(BankRecord[] robjs) {
//        try {
//            System.out.println("Inserting records into the table...");
//            stmt = conn.connect().createStatement();
//
//            for (BankRecord record : robjs) {
//                // Insert each record's id, income, pep into the table
//                String sql = "INSERT INTO a_abdu_tab (id, income, pep) VALUES ("
//                           + "'" + record.getId() + "', "
//                           + record.getIncome() + ", "
//                           + "'" + record.getPep() + "')";
//
//                stmt.executeUpdate(sql);
//            }
//            System.out.println("Records inserted successfully!");
//            conn.connect().close();
//        } catch (SQLException se) {
//            se.printStackTrace();
//        }
//    }

    // SELECT METHOD
    public ResultSet retrieveRecords() {
        ResultSet rs = null;
        try {
            System.out.println("Selecting all records from the table...");
            stmt = conn.connect().createStatement();

            String sql = "SELECT * FROM a_abdu_tab ORDER BY pep DESC";
            rs = stmt.executeQuery(sql);

            // Note: Do NOT close the connection here if you need the result set in the view
            // but for short-living use, it's often okay. As an example:
            conn.connect().close();
            System.out.println("All Records selected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
