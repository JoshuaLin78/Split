package use_cases.write_off_debt;

import entity.Debtor;

/**
 * The Input Data for the Write-Off Debt Use Case.
 */
public class WriteOffDebtInputData {
    private final Debtor debtor;
    private final double amount;

    /**
     * Constructor for the WriteOffDebtInputData class.
     * @param debtor the debtor to write off debt for
     * @param amount the amount of debt to write off
     */
    public WriteOffDebtInputData(Debtor debtor, double amount) {
        this.debtor = debtor;
        this.amount = amount;
    }

    public Debtor getDebtor() {
        return debtor;
    }

    public double getAmount() {
        return amount;
    }
}
