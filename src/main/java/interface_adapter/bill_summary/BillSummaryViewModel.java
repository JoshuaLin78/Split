package interface_adapter.bill_summary;

import interface_adapter.ViewModel;

public class BillSummaryViewModel extends ViewModel<BillSummaryState> {
    public BillSummaryViewModel() {
        super("Bill Summary");
        setState(new BillSummaryState());
    }
}
