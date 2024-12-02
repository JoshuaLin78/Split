package use_cases.bill_input;

import entity.Order;

import java.util.List;

/**
 * The Input Data for the Bill Input Use Case.
 */
public class BillInputInputData {
    private final List<Order> orders;
    private final double subtotal;
    private final double tax;
    private final double tip;
    private final double total;

    /**
     * Constructor
     * @param orders the list of all orders on the bill
     * @param tax the tax percentage of the bill (as a percentage)
     * @param tip the tip percentage of the bill (as a percentage)
     * @param total the total price of the bill
     */
    public BillInputInputData(List<Order> orders, double subtotal, double tax, double tip, double total) {
        this.orders = orders;
        this.subtotal = subtotal;
        this.tax = tax;
        this.tip = tip;
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public double getSubtotal() {
        return subtotal;
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
