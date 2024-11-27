package interface_adapter.bill_confirmation;

import interface_adapter.ViewManagerModel;
import interface_adapter.bill_input.BillInputViewModel;
import use_cases.bill_confirmation.BillConfirmationOutputBoundary;
import use_cases.bill_confirmation.BillConfirmationOutputData;

public class BillConfirmationPresenter implements BillConfirmationOutputBoundary{
    private final BillConfirmationViewModel billConfirmationViewModel;
    private final ViewManagerModel viewManagerModel;
    private final BillInputViewModel billInputViewModel;

    public BillConfirmationPresenter(ViewManagerModel viewManagerModel, BillConfirmationViewModel billConfirmationViewModel, BillInputViewModel billInputViewModel) {
        this.billConfirmationViewModel = billConfirmationViewModel;
        this.viewManagerModel = viewManagerModel;
        this.billInputViewModel = billInputViewModel;
    }

    /**
     * Prepares the success view for the BillConfirmation Use Case
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(BillConfirmationOutputData outputData) {

    }

    /**
     * Prepares the failure view for the BillConfirmation Use Case
     * @param errorMessage the message explaining the error
     */
    @Override
    public void prepareFailureView(String errorMessage) {}


    /**
     * Switches the view back to the Bill Input view.
     */
    @Override
    public void returnToBillInputView(){
        viewManagerModel.setState(billInputViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

}
