package controllers;

import java.sql.ResultSet;
import loan_app.BankRecord;
import loan_app.BankRecords;
import models.DaoModel;
import views.LoanView;

public class LoanProcessing extends BankRecords {
    // Use BankRecord[] since each element is a single record
    public static BankRecord[] robjs;
    
    // Loan Analysis Report Details
    public static void printLoanAnalysis(BankRecord[] records) {
        System.out.println("\n=== LOAN ANALYSIS REPORT (First 10 Rows) ===");
        System.out.printf("%-5s  %-10s  %-6s  %-10s  %-8s  %-3s\n",
                "Row", "ID", "Sex", "Region", "Income", "PEP");
        System.out.println("------------------------------------------------------");

        // Print up to 10 records
        for (int i = 0; i < Math.min(records.length, 10); i++) {
            System.out.printf("%-5d  %-10s  %-6s  %-10s  %-8.2f  %-3s\n",
                    (i + 1),
                    records[i].getId(),
                    records[i].getSex(),
                    records[i].getRegion(),
                    records[i].getIncome(),
                    records[i].getPep());
        }
    }


    public static void main(String[] args) {
        BankRecords br = new BankRecords();
        br.readData();
        br.processData();

        // After processing, assign BankRecords.bankRecords to our local array
        robjs = BankRecords.bankRecords;

        DaoModel dao = new DaoModel();
        dao.createTable();          // Create the table if not already existing
        dao.insertRecords(robjs);   // Perform inserts

        // Retrieve records and display in GUI table
        ResultSet rs = dao.retrieveRecords();
        new LoanView().runView(rs);
        
        // Extra credit: Display first 10 rows with a title
        printLoanAnalysis(robjs);
    }
}
