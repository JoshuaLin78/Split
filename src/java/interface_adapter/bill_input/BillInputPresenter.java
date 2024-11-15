package interface_adapter.bill_input;

import interface_adapter.ViewManagerModel;
import use_cases.bill_input.BillInputOutputBoundary;
import use_cases.bill_input.BillInputOutputData;

public class BillInputPresenter implements BillInputOutputBoundary {
    private final BillInputViewModel billInputViewModel;
    private final ViewManagerModel viewManagerModel;

    public BillInputPresenter(ViewManagerModel viewManagerModel, BillInputViewModel billInputViewModel) {
        this.billInputViewModel = billInputViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the success view for the BillInput Use Case
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(BillInputOutputData outputData) {

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
