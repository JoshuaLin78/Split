package use_cases.write_off_debt;

import entity.Debtor;
import entity.Order;

import java.util.List;

public class WriteOffDebtOutputData {
    private final List<Order> orders;
    private final double tax;
    private final double tip;
    private final double total;
    private final List<Debtor> debtors;

    public WriteOffDebtOutputData(List<Order> orders, double tax, double tip, double total, List<Debtor> debtors) {
        this.orders = orders;
        this.tax = tax;
        this.tip = tip;
        this.total = total;
        this.debtors = debtors;
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

    public List<Debtor> getDebtors() {
        return debtors;
    }


}

