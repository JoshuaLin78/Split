package interface_adapter.bill_input;

import entity.Order;

import java.util.ArrayList;
import java.util.List;

public class BillInputState {
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString(){
        return "BillInputState";
    }
}
