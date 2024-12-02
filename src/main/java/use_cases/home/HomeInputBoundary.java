package use_cases.home;

/**
 * The input boundary for the home use case.
 */
public interface HomeInputBoundary {

    /**
     * Executes the home use case
     * @param homeInputData the input data
     */
    void execute(HomeInputData homeInputData);

    /**
     * Executes the switch to new bill view use case
     */
    void switchToNewBillView();

    /**
     * Executes the switch to debtors view use case
     */
    void switchToDebtorsView();

    /**
     * Executes the switch to history view use case
     */
    void switchToHistoryView();
}
