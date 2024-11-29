package use_cases.bill_confirmation;

import entity.Debtor;

import java.util.List;

public interface BillConfirmationDebtorDataAccessInterface {

    boolean existsByName(String name);

    void saveNew(Debtor debtor);

    void update(Debtor debtor);

    Debtor get(String name);

    List<Debtor> getAll();
}
