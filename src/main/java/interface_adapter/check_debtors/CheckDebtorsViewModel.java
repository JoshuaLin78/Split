package interface_adapter.check_debtors;

import interface_adapter.ViewModel;
import interface_adapter.bill_confirmation.BillConfirmationState;

public class CheckDebtorsViewModel extends ViewModel<CheckDebtorsState> {
    public CheckDebtorsViewModel() {
        super("Bill Confirmation");
        setState(new CheckDebtorsState());
    }
}

