package interface_adapter.bill_confirmation;

import entity.Debtor;

import java.util.ArrayList;
import java.util.List;

public class BillConfirmationState {
    private List<Debtor> debtors = new ArrayList<>();

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
