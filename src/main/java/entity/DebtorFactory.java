package entity;

/**
 * Factory for creating debtors.
 */
public class DebtorFactory {
    /**
     * Creates a new debtor
     * @param name the name of the debtor
     * @param currDebt the amount of money the Debtor owes the user for the current bill
     * @param totalDebt the total amount of money the Debtor owes the user
     * @return the new debtor
     */
    public Debtor create(String name, double currDebt, double totalDebt) {
        return new Debtor(name, currDebt, totalDebt);
    }
}
