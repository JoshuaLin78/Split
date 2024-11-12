package use_cases.bill_input;

import entity.Dish;

import java.util.List;
import java.util.Map;

/**
 * The Input Data for the Bill Input Use Case.
 */
public class BillInputInputData {
    private final List<Dish> orders;
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
    public BillInputInputData(List<Dish> orders, double tax, double tip, double total) {
        this.orders = orders;
        this.tax = tax;
        this.tip = tip;
        this.total = total;
    }

    public List<Dish> getOrders() {
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
