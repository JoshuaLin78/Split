package entity;

/**
 * The representation of a debtor.
 */
public interface Debtor {

    /**
     * Returns the name of the debtor
     * @return the name of the debtor
     */
    String getName();

    /**
     * Returns the amount of money (in Canadian dollars) the debtor owes the user
     * @return the amount of money (in Canadian dollars) the debtor owes the user
     */
    double getAmountOwed();
}
