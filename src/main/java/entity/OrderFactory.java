package entity;

/**
 * Factory for creating orders.
 */
public class OrderFactory {
    /**
     * Creates a new dish
     * @param name the name of the dish
     * @param price the price of the dish in Canadian dollars
     * @param quantity the number of times this dish was ordered
     * @param consumers an array of all individuals who ordered this dish
     * @return the new dish
     */
    Order create(String name, double price, int quantity, String[] consumers){
        return new Order(name, price, quantity, consumers);
    }
}
