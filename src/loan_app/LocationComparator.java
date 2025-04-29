package loan_app;

import java.util.Comparator;

public class LocationComparator implements Comparator<BankRecord> {
    @Override
    public int compare(BankRecord o1, BankRecord o2) {
        return o1.getRegion().compareTo(o2.getRegion());
    }
}

