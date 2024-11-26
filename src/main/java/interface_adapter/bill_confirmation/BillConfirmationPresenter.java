package interface_adapter.bill_confirmation;

import interface_adapter.ViewManagerModel;
import use_cases.bill_confirmation.BillConfirmationOutputBoundary;
import use_cases.bill_confirmation.BillConfirmationOutputData;

public class BillConfirmationPresenter implements BillConfirmationOutputBoundary{
    private final BillConfirmationViewModel billConfirmationViewModel;
    private final ViewManagerModel viewManagerModel;

    public BillConfirmationPresenter(ViewManagerModel viewManagerModel, BillConfirmationViewModel billConfirmationViewModel) {
        this.billConfirmationViewModel = billConfirmationViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the success view for the BillConfirmation Use Case
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(BillConfirmationOutputData outputData) {

    }

    /**
     * Prepares the failure view for the BillConfirmation Use Case
     *
     * @param errorMessage the message explaining the error
     */
    @Override
    public void prepareFailureView(String errorMessage) {

    }

    public void displayBillConfirmationView(BillConfirmationOutputData outputData) {
        //Object[][] debtors = outputData.getDebtors();

    }
}
