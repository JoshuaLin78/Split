package use_cases.view_history;

/**
 * Input boundary for the view history functions.
 */
public interface ViewHistoryInputBoundary {

    /**
     * Executes the View History order view use case.
     */
    void execute(ViewHistoryInputData viewHistoryInputData);

    /**
     * Returns to the homepage.
     */
    void returnHome();
}
