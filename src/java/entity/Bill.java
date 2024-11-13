package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple Bill class
 */
public class Bill {

    private final List<Order> contents;

    /**
     * Initializes an empty list
     */
    public Bill() {
        contents = new ArrayList<>();
    }

    /**
     * @return A List object containing the Order object
     */
    public List<Order> getContents() {
        return contents;
    }

    /**
     * Add the given order to the stored contents
     */
    public void addOrder(Order order) {
        contents.add(order);
    }

}