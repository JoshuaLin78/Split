package use_cases.bill_input;

import entity.Debtor;

import java.util.List;

public class BillInputOutputData {
    private final List<Debtor> debtors;

    public BillInputOutputData(List<Debtor> debtors) {
        this.debtors = debtors;
    }

    public List<Debtor> getDebtors() {
        return debtors;
    }
}
