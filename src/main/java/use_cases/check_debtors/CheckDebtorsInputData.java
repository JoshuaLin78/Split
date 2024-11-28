package use_cases.check_debtors;

import entity.Debtor;

import java.util.List;

/**
 * Input Data for the Check Debtors Use Case.
 */
public class CheckDebtorsInputData {
    private final List<Debtor> debtors;

    /**
     * Constructor
     * @param debtors list of debtors
     */
    public CheckDebtorsInputData(List<Debtor> debtors) {
        this.debtors = debtors;
    }

    public List<Debtor> getDebtors() {return debtors;}
}
