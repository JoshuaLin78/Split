package use_cases.bill_confirmation;

import entity.Debtor;

import java.util.List;

public class BillConfirmationOutputData {
    private final List<Debtor> debtors;

    public BillConfirmationOutputData(List<Debtor> debtors) {
        this.debtors = debtors;
    }

    public List<Debtor> getDebtors() {
        return debtors;
    }
}
