package use_cases.bill_summary;

public class BillSummaryInteractor implements BillSummaryInputBoundary{
    private final BillSummaryOutputBoundary userPresenter;

    public BillSummaryInteractor(BillSummaryOutputBoundary userPresenter) {
        this.userPresenter = userPresenter;
    }

    /**
     * Returns the view back to order history.
     */
    @Override
    public void goBack() {
        userPresenter.goBack();
    }
}


