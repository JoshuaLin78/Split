package interface_adapter.bill_summary;

import entity.OrderSummary;

public class BillSummaryState {
    OrderSummary orderSummary;

    public OrderSummary getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(OrderSummary orderSummary) {
        this.orderSummary = orderSummary;
    }
}
