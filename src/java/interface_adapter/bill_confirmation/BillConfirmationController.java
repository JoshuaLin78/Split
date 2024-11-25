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

    public void execute(List<Debtor> debtors){
        final BillConfirmationInputData billConfirmationInputData = new BillConfirmationInputData(debtors);

        userbillConfirmationUseCaseInteractor.execute(billConfirmationInputData);
    }

    public void confirmAction(){
    }

    public void cancelAction(){}
}
