package interface_adapter.write_off_debt;

import interface_adapter.ViewModel;

public class WriteOffDebtViewModel extends ViewModel<WriteOffDebtState> {
    public WriteOffDebtViewModel() {
        super("Write Off Debt");
        setState(new WriteOffDebtState());
    }
}
