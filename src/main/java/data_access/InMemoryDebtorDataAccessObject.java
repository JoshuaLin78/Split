package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Debtor;
import use_cases.bill_confirmation.BillConfirmationDebtorDataAccessInterface;
import use_cases.bill_input.BillInputDebtorDataAccessInterface;
import use_cases.write_off_debt.WriteOffDebtDebtorDataAccessInterface;

/**
 * In memory implementation of DAO that stores debtor data.
 */
public class InMemoryDebtorDataAccessObject implements BillInputDebtorDataAccessInterface,
        BillConfirmationDebtorDataAccessInterface, WriteOffDebtDebtorDataAccessInterface {

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
    public void writeOffDebt(Debtor debtor, double amount) {
        if (debtor.getTotalDebt() <= amount) {
            debtors.remove(debtor.getName());
        }
        else {
            debtors.get(debtor.getName()).writeOffDebt(amount);
        }
    }

    @Override
    public Debtor get(String name) {
        return debtors.get(name);
    }

    @Override
    public List<Debtor> getAll() {
        return new ArrayList<>(debtors.values());
    }
}
