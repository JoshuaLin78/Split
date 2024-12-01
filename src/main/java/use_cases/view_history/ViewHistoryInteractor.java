package use_cases.view_history;

public class ViewHistoryInteractor implements ViewHistoryInputBoundary {
    private ViewHistoryOutputBoundary userPresenter;

    public ViewHistoryInteractor(ViewHistoryOutputBoundary userPresenter) {
        this.userPresenter = userPresenter;
    }

    /**
     * Switches the view to a bill summary of the specified order.
     * @param viewHistoryInputData the input data containing the order summary
     */
    @Override
    public void orderView(ViewHistoryInputData viewHistoryInputData) {
        ViewHistoryOutputData viewHistoryOutputData = new ViewHistoryOutputData(viewHistoryInputData.getOrderSummary());
        userPresenter.orderView(viewHistoryOutputData);
    }

    /**
     * Returns view back to the homepage.
     */
    @Override
    public void returnHome() {
        userPresenter.returnHome();
    }
}
