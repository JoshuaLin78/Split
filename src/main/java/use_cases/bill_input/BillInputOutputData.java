package use_cases.bill_input;

import entity.Debtor;
import entity.Order;

import java.util.List;

public class BillInputOutputData {
    private final List<Order> orders;
    private final double subtotal;
    private final double tax;
    private final double tip;
    private final double total;
    private final List<Debtor> debtors;

    public BillInputOutputData(List<Order> orders, double subtotal, double tax, double tip, double total,
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

    public List<Debtor> getDebtors() {
        return debtors;
    }
}
