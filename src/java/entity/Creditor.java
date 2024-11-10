package entity;

/**
 * The representation of a creditor.
 */
public interface Creditor {

    /**
     * Returns the name of the creditor
     * @return the name of the creditor
     */
    String getName();

    /**
     * Returns the amount of money (in Canadian dollars) the user owes the creditor
     * @return the amount of money (in Canadian dollars) the user owes the creditor
     */
    double getAmountOwed();
}
