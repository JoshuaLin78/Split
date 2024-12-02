package interface_adapter.bill_input;

import entity.Order;

import java.util.ArrayList;
import java.util.List;

public class BillInputState {
    private List<Order> orders = new ArrayList<>();

    private String[][] tableData;

    public String[][] getTableData() {
        return tableData;
    }

    public void setTableData(String[][] tableData) {
        this.tableData = tableData;
    }

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
