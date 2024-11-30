package use_cases.view_history;

import entity.OrderSummary;

public interface ViewHistoryOutputBoundary {

    void orderView(ViewHistoryOutputData viewHistoryOutputData);

    void returnHome();
}
