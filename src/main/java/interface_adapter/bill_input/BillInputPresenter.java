package interface_adapter.bill_input;

import interface_adapter.ViewManagerModel;
import interface_adapter.bill_confirmation.BillConfirmationState;
import interface_adapter.bill_confirmation.BillConfirmationViewModel;
import use_cases.bill_input.BillInputOutputBoundary;
import use_cases.bill_input.BillInputOutputData;

/**
 * Presenter for the BillInput Use Case.
 */
public class BillInputPresenter implements BillInputOutputBoundary {
    private final BillInputViewModel billInputViewModel;
    private final BillConfirmationViewModel billConfirmationViewModel;
    private final ViewManagerModel viewManagerModel;

    public BillInputPresenter(ViewManagerModel viewManagerModel, BillInputViewModel billInputViewModel,
                              BillConfirmationViewModel billConfirmationViewModel) {
        this.billInputViewModel = billInputViewModel;
        this.viewManagerModel = viewManagerModel;
        this.billConfirmationViewModel = billConfirmationViewModel;
    }

    /**
     * Prepares the success view for the BillInput Use Case
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(BillInputOutputData outputData) {
        final BillConfirmationState billConfirmationState = billConfirmationViewModel.getState();
        billConfirmationState.setOrders(outputData.getOrders());
        billConfirmationState.setSubtotal(outputData.getSubtotal());
        billConfirmationState.setTax(outputData.getTax());
        billConfirmationState.setTip(outputData.getTip());
        billConfirmationState.setTotal(outputData.getTotal());
        billConfirmationState.setDebtors(outputData.getDebtors());
        this.billConfirmationViewModel.setState(billConfirmationState);
        billConfirmationViewModel.firePropertyChanged();

        viewManagerModel.setState(billConfirmationViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the BillInput Use Case
     *
     * @param errorMessage the message explaining the error
     */
    @Override
    public void prepareFailureView(String errorMessage) {

    }
}
