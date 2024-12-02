package frameworks;

public class Examples {
    public static final String scrambledText1 = "Liquor Street\n" +
            "(ODVJH Private Limited)\n" +
            "11/2 Sector-37,\n" +
            "Faridabad-121003.\n" +
            "Ph. No.: 0129-4360377, 9311111116\n" +
            "GSTIN: 06AACCO6344G1ZJ\n" +
            "Invoice Number:\n" +
            "Invoice Date:\n" +
            "IN001001259\n" +
            "20-May-18 22:55\n" +
            "Item\n" +
            "Qty.\n" +
            "Rate Total\n" +
            "Tandoori chicken\n" +
            "Lasooni Dal Tadka\n" +
            "HYDERABADI MURG\n" +
            "BIRYANI\n" +
            "1 295.00 309.75\n" +
            "1 275.00 288.75\n" +
            "1 375.00 393.75\n" +
            "Tandoori Roti all food\n" +
            "2 30.00 63.00\n" +
            "less spicy\n" +
            "Tandoori Roti\n" +
            "1 30.00 31.50\n" +
            "Total Qty:\n" +
            "Sub Total:\n" +
            "CGST@2.5\n" +
            "SGST@2.5\n" +
            "S.Tax\n" +
            "Total:\n" +
            "Thanks For Visit....\n" +
            "6\n" +
            "1,035.00\n" +
            "25.89\n" +
            "25.89\n" +
            "51.75\n" +
            "1,139.00";

    public static final String desiredOutput1 = "[{\"Dish\":\"Tandoori chicken\",\"Quantity\":1,\"Price\":295.00}," +
            "{\"Dish\":\"Lasooni Dal Tadka\",\"Quantity\":1,\"Price\":275.00}," +
            "{\"Dish\":\"HYDERABADI MURG BIRYANI\",\"Quantity\":1,\"Price\":375.00}," +
            "{\"Dish\":\"Tandoori Roti (all food less spicy)\",\"Quantity\":2,\"Price\":30.00}," +
            "{\"Dish\":\"Tandoori Roti\",\"Quantity\":1,\"Price\":30.00}]\n";

    private Examples() {
        // Private constructor to prevent instantiation
    }
}
