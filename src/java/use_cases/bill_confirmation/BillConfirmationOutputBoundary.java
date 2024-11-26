package use_cases.bill_confirmation;

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
     * Prepares the Confirmation view for the BillConfirmation Use Case
     * @param outputData the data that will be displayed
     */
    void displayBillConfirmationView(BillConfirmationOutputData outputData);
}
