package use_cases.home;

import entity.Debtor;

public interface HomeDebtorDataAccessInterface {

    boolean existsByName(String name);

    void saveNew(Debtor debtor);

    void update(Debtor debtor);

    void writeOffDebt(Debtor debtor, double amount);

    Debtor get(String name);
}
