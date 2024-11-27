package entity;

/**
 * The debtor class.
 */
public class Debtor {
    private final String name;
    private double currDebt;
    private double totalDebt;

    /**
     * Debtor constructor
     * @param name the name of the debtor
     * @param currDebt the amount of money (in Canadian dollars) the Debtor owes the user for the current bill
     * @param totalDebt the total amount of money (in Canadian dollars) the Debtor owes the user
     */
    public Debtor(String name, double currDebt, double totalDebt) {
        this.name = name;
        this.currDebt = currDebt;
        this.totalDebt = totalDebt;
    }

    /**
     * Returns the name of the debtor
     * @return the name of the debtor
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the amount of money (in Canadian dollars) the debtor owes the user in the current bill
     * @return the amount of money (in Canadian dollars) the debtor owes the user in the current bill
     */
    public double getCurrDebt() {
        return currDebt;
    }

    /**
     * Adds to the amount of money (in Canadian dollars) the debtor owes the user in the current bill
     * @param debt the amount of money to add
     */
    public void addToCurrDebt(double debt) {currDebt += debt;}

    /**
     * Resets the amount of money (in Canadian dollars) the debtor owes the user in the current bill
     */
    public void resetCurrDebt() {currDebt = 0;}

    /**
     * Returns the total amount of money (in Canadian dollars) the debtor owes the user
     * @return the total amount of money (in Canadian dollars) the debtor owes the user
     */
    public double getTotalDebt(){
        return totalDebt;
    }

    /**
     * Adds to the total amount of money (in Canadian dollars) the debtor owes the user
     * @param debt the amount to add
     */
    public void addToTotalDebt(double debt) {totalDebt += debt;}

    /**
     * String representation of debtor
     * @return the name of the debtor
     */
    public String toString(){
        return name;
    }
}
