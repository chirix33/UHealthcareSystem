package loan_app;
import java.io.*;
import java.util.*;

public class BankRecords extends Client {
	private List<String[]> records = new ArrayList<>();
    public static BankRecord[] bankRecords;

    // Read data from CSV file into an ArrayList
    @Override
    public void readData() {
        String filePath = new File("bank-Detail.csv").getAbsolutePath();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                records.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Process data and store it in an array of objects
    @Override
    public void processData() {
        bankRecords = new BankRecord[records.size()];
        for (int i = 0; i < records.size(); i++) {
            String[] data = records.get(i);
            bankRecords[i] = new BankRecord(data[0], Integer.parseInt(data[1]), data[2], data[3],
                    Double.parseDouble(data[4]), data[5], Integer.parseInt(data[6]), data[7],
                    data[8], data[9], data[10], data[11]);
        }
    }

    // Print the first 25 records in a formatted columnar style
    @Override
    public void printData() {
        System.out.printf("%-10s %-5s %-10s %-10s %-10s %-10s\n", "ID", "AGE", "SEX", "REGION", "INCOME", "MORTGAGE");
        System.out.println("-------------------------------------------------------------");
        for (int i = 0; i < Math.min(25, bankRecords.length); i++) {
            System.out.printf("%-10s %-5d %-10s %-10s %-10.2f %-10s\n",
                    bankRecords[i].getId(),
                    bankRecords[i].getAge(),
                    bankRecords[i].getSex(),
                    bankRecords[i].getRegion(),
                    bankRecords[i].getIncome(),
                    bankRecords[i].getMortgage());
        }
    }

    public static void main(String[] args) {
        BankRecords recordsProcessor = new BankRecords();
        recordsProcessor.readData();
        recordsProcessor.processData();
        recordsProcessor.printData();
    }
}
