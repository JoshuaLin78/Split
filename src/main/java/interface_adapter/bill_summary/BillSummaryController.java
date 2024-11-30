package interface_adapter.bill_summary;

import use_cases.bill_summary.BillSummaryInputBoundary;

public class BillSummaryController {
    private final BillSummaryInputBoundary userBillSummaryUseCaseInteractor;

    public BillSummaryController(BillSummaryInputBoundary userBillSummaryUseCaseInteractor) {
        this.userBillSummaryUseCaseInteractor = userBillSummaryUseCaseInteractor;
    }

    public void goBack(){
        userBillSummaryUseCaseInteractor.goBack();
    }
}
