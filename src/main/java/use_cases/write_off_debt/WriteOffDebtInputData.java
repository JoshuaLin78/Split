package use_cases.write_off_debt;

import entity.Debtor;
import entity.Order;

import java.util.List;

/**
 * The Input Data for the Write-Off Debt Use Case.
 */
public class WriteOffDebtInputData {
    private final List<Order> orders;
    private final double subtotal;
    private final double tax;
    private final double tip;
    private final double total;
    private final List<Debtor> debtors;
    /**
     * Constructor
     * @param debtors the list of debtors
     */
    public WriteOffDebtInputData(List<Order> orders, double subtotal, double tax, double tip, double total,
                                     List<Debtor> debtors) {
        this.orders = orders;
        this.subtotal = subtotal;
        this.tax = tax;
        this.tip = tip;
        this.total = total;
        this.debtors = debtors;
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

    public List<Debtor> getDebtors() {return debtors;}
}
