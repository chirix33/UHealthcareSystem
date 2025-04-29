package loan_app;
import java.util.Comparator;

public class SexComparator implements Comparator<BankRecord> {
    @Override
    public int compare(BankRecord o1, BankRecord o2) {
        return o1.getSex().compareTo(o2.getSex());
    }
}
