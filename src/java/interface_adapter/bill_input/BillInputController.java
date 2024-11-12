package interface_adapter.bill_input;

import entity.Dish;
import use_cases.bill_input.BillInputInputData;
import use_cases.bill_input.BillInputInputBoundary;

import java.util.Map;

public class BillInputController {
    private final BillInputInputBoundary userbillInputUseCaseInteractor;

    public BillInputController(BillInputInputBoundary userbillInputUseCaseInteractor) {
        this.userbillInputUseCaseInteractor = userbillInputUseCaseInteractor;
    }

    public void execute(Map<Dish, Integer> contents, Dish[] dishes){
        final BillInputInputData billInputInputData = new BillInputInputData(contents, dishes);

        userbillInputUseCaseInteractor.execute(billInputInputData);
    }
}
