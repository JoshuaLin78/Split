package api;

import com.google.gson.*;

public class JsonTo2DArray {

    public static String[][] convertJson(String jsonString) {
        // Parse the JSON
        Gson gson = new Gson();
        Dish[] dishes = gson.fromJson(jsonString, Dish[].class);

        // Convert to a 2D array
        String[][] dishArray = new String[dishes.length][3];
        for (int i = 0; i < dishes.length; i++) {
            dishArray[i][0] = dishes[i].getDish();
            dishArray[i][1] = String.valueOf(dishes[i].getQuantity());
            dishArray[i][2] = String.valueOf(dishes[i].getPrice());
        }

        return dishArray;
    }

    // POJO class for parsing JSON
    static class Dish {
        private String Dish;
        private int Quantity;
        private double Price;

        public String getDish() {
            return Dish;
        }

        public int getQuantity() {
            return Quantity;
        }

        public double getPrice() {
            return Price;
        }
    }
}
