package entity;

/**
 * Factory for creating dishes.
 */
public interface DishFactory {
    /**
     * Creates a new dish
     * @param name the name of the dish
     * @param price the price of the dish in Canadian dollars
     * @param consumers an array of all individuals who ordered this dish
     * @return the new dish
     */
    Dish create(String name, double price, String[] consumers);
}
