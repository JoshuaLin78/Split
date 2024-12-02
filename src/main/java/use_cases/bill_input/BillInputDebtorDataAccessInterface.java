package use_cases.bill_input;

import entity.Debtor;

/**
 * Debtor DAO for the bill input use case.
 */
public interface BillInputDebtorDataAccessInterface {
    Debtor get(String name);
}
