package interface_adapter.bill_confirmation;

import entity.Debtor;
import entity.Order;
import use_cases.bill_confirmation.BillConfirmationInputBoundary;
import use_cases.bill_confirmation.BillConfirmationInputData;

import java.util.List;

/**
 * Controller for the bill confirmation view.
 */
public class BillConfirmationController {
    private final BillConfirmationInputBoundary userbillConfirmationUseCaseInteractor;

    public BillConfirmationController(BillConfirmationInputBoundary userbillConfirmationUseCaseInteractor) {
        this.userbillConfirmationUseCaseInteractor = userbillConfirmationUseCaseInteractor;
    }

    /**
     * Executes the bill confirmation use case.
     * @param orders The list of orders.
     * @param subtotal The subtotal of the bill.
     * @param tax The tax of the bill.
     * @param tip The tip of the bill.
     * @param total The total price of the bill.
     * @param debtors The list of debtors, people (not including the user) who ordered at least one item on the bill.
     */
    public void execute(List<Order> orders, double subtotal, double tax, double tip, double total, List<Debtor> debtors){
        final BillConfirmationInputData billConfirmationInputData = new BillConfirmationInputData(orders, subtotal, tax,
                tip, total, debtors);

        userbillConfirmationUseCaseInteractor.execute(billConfirmationInputData);
    }

    public void returnToBillInputView(){
        userbillConfirmationUseCaseInteractor.returnToBillInputView();
    }

}
