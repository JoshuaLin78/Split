package interface_adapter.bill_confirmation;

import entity.Order;
import use_cases.bill_confirmation.BillConfirmationInputBoundary;
import use_cases.bill_confirmation.BillConfirmationInputData;

import java.util.List;

public class BillConfirmationController {
    private final BillConfirmationInputBoundary userbillConfirmationUseCaseInteractor;

    public BillConfirmationController(BillConfirmationInputBoundary userbillConfirmationUseCaseInteractor) {
        this.userbillConfirmationUseCaseInteractor = userbillConfirmationUseCaseInteractor;
    }

    public void execute(List<Order> orders, double tax, double tip, double total){
        final BillConfirmationInputData billConfirmationInputData = new BillConfirmationInputData(orders, tax, tip, total);

        userbillConfirmationUseCaseInteractor.execute(billConfirmationInputData);
    }
}
