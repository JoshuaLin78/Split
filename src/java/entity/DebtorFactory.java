package entity;

/**
 * Factory for creating debtors.
 */
public class DebtorFactory {
    /**
     * Creates a new debtor
     * @param name the name of the debtor
     * @param amountOwed the amount of money (in Canadian dollars) the debtor owes the user
     * @return the new debtor
     */
    Debtor create(String name, double amountOwed) {
        return new Debtor(name, amountOwed);
    }
}
