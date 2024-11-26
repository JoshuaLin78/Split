package use_cases.bill_input;

public interface BillInputOutputBoundary {

    /**
     * Prepares the success view for the BillInput Use Case
     * @param outputData the output data
     */
    void prepareSuccessView(BillInputOutputData outputData);

    /**
     * Prepares the failure view for the BillInput Use Case
     * @param errorMessage the message explaining the error
     */
    void prepareFailureView(String errorMessage);
}
