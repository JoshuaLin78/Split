package use_cases.bill_confirmation;

import entity.DebtorFactory;

public class BillConfirmationInteractor implements BillConfirmationInputBoundary {
    private final BillConfirmationOutputBoundary userPresenter;
    private final DebtorFactory debtorFactory;

    public BillConfirmationInteractor(BillConfirmationOutputBoundary billConfirmationOutputBoundary, DebtorFactory debtorFactory) {
        this.userPresenter = billConfirmationOutputBoundary;
        this.debtorFactory = debtorFactory;
    }

    /**
     * Creates a list of debtors, each with the debt they owe the user from the current bill, and sends it to presenter
     * @param billConfirmationInputData the input data for bill input
     */
    @Override
    public void execute(BillConfirmationInputData billConfirmationInputData) {

    }
}
