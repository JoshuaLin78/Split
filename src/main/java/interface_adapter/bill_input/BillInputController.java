package interface_adapter.bill_input;

import entity.Order;
import use_cases.bill_input.BillInputInputData;
import use_cases.bill_input.BillInputInputBoundary;

import java.util.List;

/**
 * Controller for the bill input view.
 */
public class BillInputController {
    private final BillInputInputBoundary userbillInputUseCaseInteractor;

    public BillInputController(BillInputInputBoundary userbillInputUseCaseInteractor) {
        this.userbillInputUseCaseInteractor = userbillInputUseCaseInteractor;
    }

    /**
     * Executes the bill input use case.
     * @param orders The list of orders.
     * @param subtotal The subtotal of the bill.
     * @param tax The tax of the bill.
     * @param tip The tip of the bill.
     * @param total The total price of the bill.
     */
    public void execute(List<Order> orders, double subtotal, double tax, double tip, double total){
        final BillInputInputData billInputInputData = new BillInputInputData(orders, subtotal, tax, tip, total);

        userbillInputUseCaseInteractor.execute(billInputInputData);
    }
}
