package use_cases.bill_confirmation;

import entity.Debtor;
import entity.Order;

import java.util.List;

public class BillConfirmationOutputData {
    private final List<Order> orders;
    private final double tax;
    private final double tip;
    private final double total;
    private final List<Debtor> billDebtors;
    private final List<Debtor> allDebtors;

    public BillConfirmationOutputData(List<Order> orders, double tax, double tip, double total,
                                      List<Debtor> billDebtors, List<Debtor> allDebtors) {
        this.orders = orders;
        this.tax = tax;
        this.tip = tip;
        this.total = total;
        this.billDebtors = billDebtors;
        this.allDebtors = allDebtors;
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

    public List<Debtor> getBillDebtors() {
        return billDebtors;
    }

    public List<Debtor> getAllDebtors() {
        return allDebtors;
    }
}

