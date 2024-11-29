package interface_adapter.check_debtors;

import entity.Debtor;

import java.util.ArrayList;
import java.util.List;

public class CheckDebtorsState {
    public List<Debtor> debtors = new ArrayList<>();

    public List<Debtor> getDebtors() {return debtors;}

    public void setDebtors(List<Debtor> debtors) {
        this.debtors = debtors;
    }

    @Override
    public String toString() {
        return ("CheckDebtorsState");
    }

    public double getTotal() {
        double total = 0.00;
        for (Debtor debtor : debtors) {
            total += debtor.getTotalDebt();
        }
        return total;
    }
}

