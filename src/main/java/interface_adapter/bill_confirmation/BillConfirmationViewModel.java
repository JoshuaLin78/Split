package interface_adapter.bill_confirmation;

import interface_adapter.ViewModel;

public class BillConfirmationViewModel extends ViewModel<BillConfirmationState> {
    public BillConfirmationViewModel() {
        super("Bill Confirmation");
        setState(new BillConfirmationState());
    }
}
