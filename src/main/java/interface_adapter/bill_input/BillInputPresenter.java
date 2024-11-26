package interface_adapter.bill_input;

import use_cases.bill_input.BillInputOutputBoundary;
import use_cases.bill_input.BillInputOutputData;

public class BillInputPresenter implements BillInputOutputBoundary {
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
