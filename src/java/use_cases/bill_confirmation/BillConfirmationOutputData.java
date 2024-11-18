package use_cases.bill_confirmation;

import entity.Debtor;

import java.util.List;

public class BillConfirmationOutputData {
    private final Object[][] debtors;

    public BillConfirmationOutputData(Object[][] debtors) {
        this.debtors = debtors;
    }

    public Object[][] getDebtors() {
        return debtors;
    }


}

