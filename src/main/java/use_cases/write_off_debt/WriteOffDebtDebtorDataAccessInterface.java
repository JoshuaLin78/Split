package use_cases.write_off_debt;

import entity.Debtor;

import java.util.List;

/**
 * Debtor DAO for the write-off debt use case.
 */
public interface WriteOffDebtDebtorDataAccessInterface {
    void writeOffDebt(Debtor debtor, double amount);

    Debtor get(String name);

    List<Debtor> getAll();

    void saveNew(Debtor debtor);
}
