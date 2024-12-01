package use_cases.view_history;

import entity.OrderSummary;

public class ViewHistoryInputData {
    private final OrderSummary orderSummary;

    public ViewHistoryInputData(OrderSummary orderSummary) {
        this.orderSummary = orderSummary;
    }

    public OrderSummary getOrderSummary() {
        return orderSummary;
    }
}
