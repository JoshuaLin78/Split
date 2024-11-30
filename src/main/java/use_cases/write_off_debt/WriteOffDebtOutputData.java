package use_cases.write_off_debt;

import entity.Debtor;

import java.util.List;

public class WriteOffDebtOutputData {
    private final List<Debtor> debtors;

    public WriteOffDebtOutputData(List<Debtor> debtors) {
        this.debtors = debtors;
    }

    public List<Debtor> getDebtors() {
        return debtors;
    }
}

