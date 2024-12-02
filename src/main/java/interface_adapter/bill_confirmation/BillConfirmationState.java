package interface_adapter.bill_confirmation;

import entity.Debtor;
import entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * State for the Bill Confirmation View Model
 */
public class BillConfirmationState {
    private List<Order> orders = new ArrayList<>();
    private double subtotal = 0.0;
    private double tax = 0.0;
    private double tip = 0.0;
    private double total = 0.0;
    private List<Debtor> debtors = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Debtor> getDebtors(){
        return debtors;
    }

    public void setDebtors(List<Debtor> debtors){
        this.debtors = debtors;
    }

    @Override
    public String toString(){
        return "BillConfirmationState";
    }
}
