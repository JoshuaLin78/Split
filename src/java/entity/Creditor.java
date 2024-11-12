package entity;

/**
 * The creditor class.
 */
public class Creditor {
    private final String name;
    private final double amount;

    /**
     * Creditor constructor
     * @param name the name of the creditor
     * @param amount the amount of money (in Canadian dollars) the use owes the creditor
     */
    public Creditor(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Returns the name of the creditor
     * @return the name of the creditor
     */
    String getName() {
        return name;
    }

    /**
     * Returns the amount of money (in Canadian dollars) the user owes the creditor
     * @return the amount of money (in Canadian dollars) the user owes the creditor
     */
    double getAmountOwed(){
        return amount;
    }
}
