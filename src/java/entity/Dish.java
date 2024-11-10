package entity;

/**
 * The representation of a single dish in the order
 */
public interface Dish {

    /**
     * Returns the name of the dish
     * @return the name of the dish
     */
    String getName();

    /**
     * Returns the price of the dish in Canadian dollars
     * @return the price of the dish in Canadian dollars
     */
    double getPrice();

    /**
     * Returns an array of all individuals who ordered this dish
     * @return an array of all individuals who ordered this dish
     */
    String[] getConsumers();
}
