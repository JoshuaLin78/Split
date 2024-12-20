package use_cases.bill_confirmation;

/**
 * Output boundary for the BillConfirmation Use Case
 */
public interface BillConfirmationOutputBoundary {

    /**
     * Prepares the success view for the BillConfirmation Use Case
     * @param outputData the output data
     */
    void prepareSuccessView(BillConfirmationOutputData outputData);

    /**
     * Prepares the failure view for the BillConfirmation Use Case
     * @param errorMessage the message explaining the error
     */
    void prepareFailureView(String errorMessage);

    /**
     * Returns to Bill Input View for editing.
     */
    void returnToBillInputView();
}
