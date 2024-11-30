package use_cases.write_off_debt;

import entity.Debtor;

import java.util.List;

public interface WriteOffDebtDebtorDataAccessInterface {

    boolean existsByName(String name);

    void saveNew(Debtor debtor);

    void update(Debtor debtor);

    void writeOffDebt(Debtor debtor, double amount);

    Debtor get(String name);

    List<Debtor> getAll();
}
