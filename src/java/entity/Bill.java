package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple Bill class
 */
public class Bill {

    private final Map<Dish, Integer> contents;

    /**
     * Initializes an empty Map
     */
    public Bill() {
        contents = new HashMap<>();
    }

    /**
     * @return A Map object containing the Dish object and its associated quantity
     */
    public Map getContents() {
        return contents;
    }

    /**
     * Add the given dish object and its associated quantity to the stored contents
     */
    public void addContent(Dish dish, int amount) {
        contents.put(dish, amount);
    }

}