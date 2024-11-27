package interface_adapter.bill_confirmation;

import entity.Debtor;
import entity.Order;
import use_cases.bill_confirmation.BillConfirmationInputBoundary;
import use_cases.bill_confirmation.BillConfirmationInputData;

import java.util.List;

public class BillConfirmationController {
    private final BillConfirmationInputBoundary userbillConfirmationUseCaseInteractor;

    public BillConfirmationController(BillConfirmationInputBoundary userbillConfirmationUseCaseInteractor) {
        this.userbillConfirmationUseCaseInteractor = userbillConfirmationUseCaseInteractor;
    }

    public void execute(List<Order> orders, double subtotal, double tax, double tip, double total, List<Debtor> debtors){
        final BillConfirmationInputData billConfirmationInputData = new BillConfirmationInputData(orders, subtotal, tax,
                tip, total, debtors);

        userbillConfirmationUseCaseInteractor.execute(billConfirmationInputData);
    }

    public void returnToBillInputView(){
        userbillConfirmationUseCaseInteractor.returnToBillInputView();
    }

}
