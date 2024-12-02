package entity;

import java.util.List;

/**
 * Factory for creating an Order Summary.
 */
public class OrderSummaryFactory {

    /**
     * Creates a new order summary
     * @param orders list of the orders.
     * @param tax tax in dollars.
     * @param tip tip in dollars.
     * @param subtotal the subtotal
     * @param total total in dollars.
     * @return the new dish
     */
    public OrderSummary create(List<Order> orders, double tax, double tip, double subtotal, double total){
        return new OrderSummary(orders, tax, tip, subtotal, total);
    }
}
