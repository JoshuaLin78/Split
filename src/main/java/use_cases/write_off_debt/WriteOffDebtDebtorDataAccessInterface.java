package use_cases.write_off_debt;

import entity.Debtor;

public interface WriteOffDebtDebtorDataAccessInterface {

    boolean existsByName(String name);

    void saveNew(Debtor debtor);

    void update(Debtor debtor);

    Debtor get(String name);
}
