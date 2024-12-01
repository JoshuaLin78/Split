package interface_adapter.view_history;

import entity.Debtor;
import entity.Order;
import entity.OrderSummary;

import java.util.ArrayList;
import java.util.List;

public class ViewHistoryState {
    private List<OrderSummary> orderSummaries;

    public List<OrderSummary> getOrderSummaries() {
        return orderSummaries;
    }

    public void setOrderSummaries(List<OrderSummary> orderSummaries) {
        this.orderSummaries = orderSummaries;
    }

    public void addOrderSummary(OrderSummary orderSummary) {
        if (this.orderSummaries == null) {
            this.orderSummaries = new ArrayList<>();
        }
        this.orderSummaries.add(orderSummary);
    }

    @Override
    public String toString(){return "ViewHistoryState";}
}
