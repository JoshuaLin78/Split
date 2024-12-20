package entity;

/**
 * Factory for creating bills.
 */
public class BillFactory {
    /**
     * Creates a new bill
     * @return an empty bill
     */
    public Bill create(){ return new Bill(); }
}
