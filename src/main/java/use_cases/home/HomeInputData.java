package use_cases.home;

import entity.Debtor;

/**
 * The Input Data for the Write-Off Debt Use Case.
 */
public class HomeInputData {
    private final Debtor debtor;
    private final double amount;

    /**
     * Constructor for the HomeInputData class.
     * @param debtor the debtor to write off debt for
     * @param amount the amount of debt to write off
     */
    public HomeInputData(Debtor debtor, double amount) {
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
