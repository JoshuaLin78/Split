package interface_adapter.check_debtors;

import interface_adapter.ViewModel;

public class CheckDebtorsViewModel extends ViewModel<CheckDebtorsState> {
    public CheckDebtorsViewModel() {
        super("Check Debtors");
        setState(new CheckDebtorsState());
    }
}

