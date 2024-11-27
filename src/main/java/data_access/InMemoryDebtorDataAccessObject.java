package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.Debtor;
import use_cases.bill_confirmation.BillConfirmationDebtorDataAccessInterface;
import use_cases.bill_input.BillInputDebtorDataAccessInterface;

public class InMemoryDebtorDataAccessObject implements BillInputDebtorDataAccessInterface,
        BillConfirmationDebtorDataAccessInterface {
    private final Map<String, Debtor> debtors = new HashMap<>();


    @Override
    public boolean existsByName(String name) {
        return debtors.containsKey(name);
    }

    @Override
    public void saveNew(Debtor debtor) {
        debtor.addToTotalDebt(debtor.getCurrDebt());
        debtor.resetCurrDebt();
        debtors.put(debtor.getName(), debtor);
    }

    @Override
    public void update(Debtor debtor) {
        debtors.get(debtor.getName()).addToTotalDebt(debtor.getCurrDebt());
        debtors.get(debtor.getName()).resetCurrDebt();
    }

    @Override
    public Debtor get(String name) {
        return debtors.get(name);
    }
}
