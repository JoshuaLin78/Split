package interface_adapter.bill_input;

import entity.Order;
import use_cases.bill_input.BillInputInputData;
import use_cases.bill_input.BillInputInputBoundary;

import java.util.List;

public class BillInputController {
    private final BillInputInputBoundary userbillInputUseCaseInteractor;

    public BillInputController(BillInputInputBoundary userbillInputUseCaseInteractor) {
        this.userbillInputUseCaseInteractor = userbillInputUseCaseInteractor;
    }

    public void execute(List<Order> orders, double tax, double tip, double total){
        final BillInputInputData billInputInputData = new BillInputInputData(orders, tax, tip, total);

        userbillInputUseCaseInteractor.execute(billInputInputData);
    }
}
