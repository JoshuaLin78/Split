package interface_adapter.home;

import entity.Debtor;

public class HomeState {
    private Debtor debtor = null;
    private double amount = 0.0;

    public Debtor getDebtor(){
        return debtor;
    }

    public void setDebtor(Debtor debtor){
        this.debtor = debtor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString(){
        return "HomeState";
    }
}
