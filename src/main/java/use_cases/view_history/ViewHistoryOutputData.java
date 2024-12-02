package use_cases.view_history;

import entity.OrderSummary;

/**
 * The output data for the view history use case.
 */
public class ViewHistoryOutputData {
    private OrderSummary orderSummary;

    public ViewHistoryOutputData(OrderSummary orderSummary) {
        this.orderSummary = orderSummary;
    }

    public OrderSummary getOrderSummary() {
        return orderSummary;
    }
}
