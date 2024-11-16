package use_cases.bill_confirmation;

import entity.Order;

import java.util.List;

/**
 * The Input Data for the Bill Confirmation Use Case. (Not Updated)
 */
public class BillConfirmationInputData {
    private final List<Order> orders;
    private final double tax;
    private final double tip;
    private final double total;

    /**
     * Constructor
     * @param orders the list of all orders on the bill
     * @param tax the tax percentage of the bill
     * @param tip the tip percentage of the bill
     * @param total the total price of the bill
     */
    public BillConfirmationInputData(List<Order> orders, double tax, double tip, double total) {
        this.orders = orders;
        this.tax = tax;
        this.tip = tip;
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public double getTax() {
        return tax;
    }

    public double getTip() {
        return tip;
    }

    public double getTotal() {
        return total;
    }
}
