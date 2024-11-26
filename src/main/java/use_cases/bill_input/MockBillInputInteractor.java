package use_cases.bill_input;

import use_cases.bill_input.BillInputInputBoundary;
import use_cases.bill_input.BillInputInputData;

public class MockBillInputInteractor implements BillInputInputBoundary {

    @Override
    public void execute(BillInputInputData billInputInputData) {
        System.out.println("Mock execute called with:");
        System.out.println("Orders: " + billInputInputData.getOrders());
        System.out.println("Tax: " + billInputInputData.getTax());
        System.out.println("Tip: " + billInputInputData.getTip());
        System.out.println("Total: " + billInputInputData.getTotal());
    }
}

