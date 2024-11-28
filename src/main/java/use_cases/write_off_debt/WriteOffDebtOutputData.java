package use_cases.write_off_debt;

import entity.Debtor;

public class WriteOffDebtOutputData {
    private final Debtor debtor;

    public WriteOffDebtOutputData(Debtor debtor) {
        this.debtor = debtor;
    }

    public Debtor getDebtor() {
        return debtor;
    }
}

