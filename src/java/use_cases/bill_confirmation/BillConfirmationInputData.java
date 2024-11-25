package use_cases.bill_confirmation;

import entity.Debtor;

import java.util.List;

/**
 * The Input Data for the Bill Confirmation Use Case.
 */
public class BillConfirmationInputData {
    private final List<Debtor> debtors;
    /**
     * Constructor
     * @param debtors the list of debtors
     */
    public BillConfirmationInputData(List<Debtor> debtors) {
        this.debtors = debtors;
    }

    public List<Debtor> getDebtors() {return debtors;}
}
