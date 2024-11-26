package interface_adapter.bill_input;

import interface_adapter.ViewModel;

public class BillInputViewModel extends ViewModel<BillInputState> {
    public BillInputViewModel() {
        super("Bill Input");
        setState(new BillInputState());
    }
}
