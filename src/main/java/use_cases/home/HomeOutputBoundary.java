package use_cases.home;

/**
 * The output boundary for the Home Use Case.
 */
public interface HomeOutputBoundary {

    /**
     * Prepares the success view for the Home Use Case
     * @param outputData the output data
     */
    void prepareSuccessView(HomeOutputData outputData);

    /**
     * Prepares the failure view for the Home Use Case
     * @param errorMessage the message explaining the error
     */
    void prepareFailureView(String errorMessage);

    /**
     * Switches to the Bill Input View
     */
    void switchToNewBillView();

    /**
     * Switches to the Debtors View
     */
    void switchToDebtorsView();

    /**
     * Switches to the History View
     */
    void switchToHistoryView();
}
