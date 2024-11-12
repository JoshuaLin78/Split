package entity;

/**
 * The dish class.
 */
public class Dish {
    private final String name;
    private final double price;
    private final String[] consumers;

    /**
     * Dish Constructor
     * @param name the name of the dish
     * @param price the price of the dish (in Canadian dollars)
     * @param consumers an array of all individuals who ordered this dish
     */
    public Dish(String name, double price, String[] consumers) {
        this.name = name;
        this.price = price;
        this.consumers = consumers;
    }

    /**
     * Returns the name of the dish
     * @return the name of the dish
     */
    String getName(){
        return name;
    }

    /**
     * Returns the price of the dish in Canadian dollars
     * @return the price of the dish in Canadian dollars
     */
    double getPrice(){
        return price;
    }

    /**
     * Returns an array of all individuals who ordered this dish
     * @return an array of all individuals who ordered this dish
     */
    String[] getConsumers(){
        return consumers;
    }
}
