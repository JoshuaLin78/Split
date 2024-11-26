package entity;

/**
 * Factory for creating creditors.
 */
public class CreditorFactory {
    /**
     * Creates a new creditor
     * @param name the name of the creditor
     * @param amountOwed the amount of money (in Canadian dollars) the user owes the creditor
     * @return the new creditor
     */
    Creditor create(String name, double amountOwed){
        return new Creditor(name, amountOwed);
    }
}
