package use_cases.view_history;

import entity.OrderSummary;

public interface ViewHistoryOutputBoundary {

    /**
     * Prepares the switch to the view of the bill summary of the order.
     * @param viewHistoryOutputData
     */
    void orderView(ViewHistoryOutputData viewHistoryOutputData);

    /**
     * Returns the view back to the homepage.
     */
    void returnHome();
}
