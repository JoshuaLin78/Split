package entity;

import java.util.List;

/**
 * Represents a list of all orders in a single bill.
 */
public class OrderSummary {
    List<Order> orders;
    private double subtotal;
    private double tax;
    private double tip;
    private double total;

    /**
     * OrderSummary constructor.
     * @param orders list of the orders.
     * @param tax tax in dollars.
     * @param tip tip in dollars.
     * @param total total in dollars.
     */
    public OrderSummary(List<Order> orders, double tax, double tip, double total) {
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