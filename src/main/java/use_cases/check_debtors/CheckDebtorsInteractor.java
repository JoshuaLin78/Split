package use_cases.check_debtors;

import entity.Debtor;
import entity.DebtorFactory;
import use_cases.bill_input.BillInputOutputBoundary;

import java.util.List;
import java.util.ArrayList;

public class CheckDebtorsInteractor implements CheckDebtorsInputBoundary {
    private final CheckDebtorsOutputBoundary userPresenter;

    public CheckDebtorsInteractor(CheckDebtorsOutputBoundary userPresenter) {
        this.userPresenter = userPresenter;
    }

    /**
     * Returns the view to the home page.
     */
    @Override
    public void switchView() {
        userPresenter.switchView();
    }
}
