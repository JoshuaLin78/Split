package use_cases.bill_input;

import entity.Debtor;

public class BillInputOutputData {
    private final Debtor debtors;

    public BillInputOutputData(Debtor debtors) {
        this.debtors = debtors;
    }

    public Debtor getDebtors() {
        return debtors;
    }
}
