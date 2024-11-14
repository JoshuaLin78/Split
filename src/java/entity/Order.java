package entity;

/**
 * The order class.
 */
public class Order {
    private final String name;
    private final double price;
    private final int quantity;
    private final String[] consumers;

    /**
     * Order Constructor
     * @param name the name of the dish
     * @param price the price of the dish (in Canadian dollars)
     * @param quantity the number of times this dish was ordered
     * @param consumers an array of all individuals who ordered this dish
     */
    public Order(String name, double price, int quantity, String[] consumers) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.consumers = consumers;
    }

    /**
     * Returns the name of the dish
     * @return the name of the dish
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the price of the dish in Canadian dollars
     * @return the price of the dish in Canadian dollars
     */
    public double getPrice(){
        return price;
    }

    /**
     * Returns the quantity of the dish
     * @return the number of times the dish was ordered
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * Returns an array of all individuals who ordered this dish
     * @return an array of all individuals who ordered this dish
     */
    public String[] getConsumers(){
        return consumers;
    }
}
