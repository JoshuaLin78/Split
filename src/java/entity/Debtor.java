package entity;

/**
 * The debtor class.
 */
public class Debtor {
    private final String name;
    private final double amount;

    /**
     * Debtor constructor
     * @param name the name of the debtor
     * @param amount the amount of money (in Canadian dollars) the Debtor owes the user
     */
    public Debtor(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Returns the name of the debtor
     * @return the name of the debtor
     */
    String getName(){
        return name;
    }

    /**
     * Returns the amount of money (in Canadian dollars) the debtor owes the user
     * @return the amount of money (in Canadian dollars) the debtor owes the user
     */
    double getAmountOwed(){
        return amount;
    }
}
