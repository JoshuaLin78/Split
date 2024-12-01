package use_cases.view_history;

import entity.OrderSummary;

public class ViewHistoryOutputData {
    private OrderSummary orderSummary;

    public ViewHistoryOutputData(OrderSummary orderSummary) {
        this.orderSummary = orderSummary;
    }

    public OrderSummary getOrderSummary() {
        return orderSummary;
    }
}
