package entity;

/**
 * Factory for creating debtors.
 */
public class DebtorFactory {
    /**
     * Creates a new debtor
     * @param name the name of the debtor
     * @param currDebt the amount of money (in Canadian dollars) the Debtor owes the user for the current bill
     * @param totalDebt the total amount of money (in Canadian dollars) the Debtor owes the user
     * @return the new debtor
     */
    Debtor create(String name, double currDebt, double totalDebt) {
        return new Debtor(name, currDebt, totalDebt);
    }
}
