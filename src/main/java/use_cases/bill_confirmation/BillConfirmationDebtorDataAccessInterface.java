package use_cases.bill_confirmation;

import entity.Debtor;

import java.util.List;

/**
 * Debtor DAO for the bill confirmation use case.
 */
public interface BillConfirmationDebtorDataAccessInterface {

    /**
     * Checks if a debtor with the given name exists.
     * @param name The name of the debtor.
     * @return True if the debtor exists, false otherwise.
     */
    boolean existsByName(String name);

    /**
     * Saves a new debtor to the DAO.
     * @param debtor The debtor to save.
     */
    void saveNew(Debtor debtor);

    /**
     * Updates an existing debtor in the DAO.
     * @param debtor The debtor to update.
     */
    void update(Debtor debtor);

    /**
     * Gets a debtor from the DAO by name
     * @param name the name of the debtor
     * @return the debtor with the given name
     */
    Debtor get(String name);

    /**
     * Gets all debtors from the DAO.
     * @return A list of all debtors.
     */
    List<Debtor> getAll();
}
