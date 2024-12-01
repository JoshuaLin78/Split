package use_cases.write_off_debt;

import entity.Debtor;

import java.util.List;

/**
 * The output data for the write-off debt use case.
 */
public class WriteOffDebtOutputData {
    private final List<Debtor> debtors;

    public WriteOffDebtOutputData(List<Debtor> debtors) {
        this.debtors = debtors;
    }

    public List<Debtor> getDebtors() {
        return debtors;
    }
}

