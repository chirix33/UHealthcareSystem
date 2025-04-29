package loan_app;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Records extends BankRecords {
    static FileWriter fw = null;

    public Records() {
        try {
            fw = new FileWriter("bankrecords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Records records = new Records();
        records.readData();
        records.processData();

        // Call analytics functions
        AvgComp();
        femsComp();
        malesComp();
        appendFooter();  // Append name & timestamp

        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Calculate average income by gender
    private static void AvgComp() {
        Arrays.sort(bankRecords, new SexComparator());

        int maleCt = 0, femCt = 0;
        double maleInc = 0, femInc = 0;

        for (BankRecord record : bankRecords) {
            if (record.getSex().equalsIgnoreCase("FEMALE")) {
                femCt++;
                femInc += record.getIncome();
            } else {
                maleCt++;
                maleInc += record.getIncome();
            }
        }

        System.out.printf("Avg inc. for Females: $%.2f\n", femInc / femCt);
        System.out.printf("Avg inc. for Males: $%.2f\n", maleInc / maleCt);

        try {
            fw.write("Avg inc. for Females: $" + String.format("%.2f", femInc / femCt) + "\n");
            fw.write("Avg inc. for Males: $" + String.format("%.2f", maleInc / maleCt) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Count females with mortgage & savings account
    private static void femsComp() {
        int femCount = 0;

        for (BankRecord record : bankRecords) {
            if (record.getSex().equalsIgnoreCase("FEMALE") &&
                record.getMortgage().equalsIgnoreCase("YES") &&
                record.getSaveAct().equalsIgnoreCase("YES")) {
                femCount++;
            }
        }

        System.out.println("Num. of Females with Mortgage & Savings Account: " + femCount);

        try {
            fw.write("Num. of Females with Mortgage & Savings Account: " + femCount + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Count males in different regions with car & 1 child
    private static void malesComp() {
        int innerCityCount = 0, ruralCount = 0, suburbanCount = 0, townCount = 0;

        for (BankRecord record : bankRecords) {
            if (record.getSex().equalsIgnoreCase("MALE") &&
                record.getCar().equalsIgnoreCase("YES") &&
                record.getChildren() == 1) {

                switch (record.getRegion().toUpperCase()) {
                    case "INNER_CITY":
                        innerCityCount++;
                        break;
                    case "RURAL":
                        ruralCount++;
                        break;
                    case "SUBURBAN":
                        suburbanCount++;
                        break;
                    case "TOWN":
                        townCount++;
                        break;
                }
            }
        }

        System.out.println("Num. of Inner-City males with car & 1 child: " + innerCityCount);
        System.out.println("Num. of Rural males with car & 1 child: " + ruralCount);
        System.out.println("Num. of Suburban males with car & 1 child: " + suburbanCount);
        System.out.println("Num. of Town males with car & 1 child: " + townCount);

        try {
            fw.write("Num. of Inner-City males with car & 1 child: " + innerCityCount + "\n");
            fw.write("Num. of Rural males with car & 1 child: " + ruralCount + "\n");
            fw.write("Num. of Suburban males with car & 1 child: " + suburbanCount + "\n");
            fw.write("Num. of Town males with car & 1 child: " + townCount + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append name and timestamp at the end of the file
    private static void appendFooter() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        try {
            fw.write("\n----------------------------------------\n");
            fw.write("Processed by: Ashraf Abdul-Muumin\n");
            fw.write("Date/Time: " + formattedDateTime + "\n");
            fw.write("----------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
