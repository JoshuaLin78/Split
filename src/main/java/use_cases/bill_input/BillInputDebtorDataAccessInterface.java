package use_cases.bill_input;

import entity.Debtor;

public interface BillInputDebtorDataAccessInterface {

    boolean existsByName(String name);

    void saveNew(Debtor debtor);

    void update(Debtor debtor);

    Debtor get(String name);
}
