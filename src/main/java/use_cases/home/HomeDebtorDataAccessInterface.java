package use_cases.home;

import entity.Debtor;

/**
 * Debtor DAO for the home use case.
 */
public interface HomeDebtorDataAccessInterface {
    Debtor get(String name);
}
