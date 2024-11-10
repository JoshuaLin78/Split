package use_cases.bill_input;

import entity.Dish;

import java.util.Map;

/**
 * The Input Data for the Bill Input Use Case.
 */
public class BillInputInputData {
    private final Map<Dish, Integer> contents;
    private final Dish[] dishes;

    /**
     * Constructor
     * @param contents a map of the contents of the bill, mapping the dish to its quantity
     * @param dishes an array of Dish objects
     */
    public BillInputInputData(Map<Dish, Integer> contents, Dish[] dishes) {
        this.contents = contents;
        this.dishes = dishes;
    }

    Map<Dish, Integer> getContents() {
        return contents;
    }

    public Dish[] getDishes() {
        return dishes;
    }
}
