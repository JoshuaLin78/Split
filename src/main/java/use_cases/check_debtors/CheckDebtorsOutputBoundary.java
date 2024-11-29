package use_cases.check_debtors;

public interface CheckDebtorsOutputBoundary {

    /**
     * Prepares the success view for the check debtors use case.
     * @param checkDebtorsOutputData the output data for the use case.
     */
    void prepareSuccessView(CheckDebtorsOutputBoundary checkDebtorsOutputData);

    /**
     * Prepares the failure view for the check debtors use case.
     *
     */
    void prepareFailureView();

    /**
     * Switches the view back to the home page.
     */
    void switchView();
}
