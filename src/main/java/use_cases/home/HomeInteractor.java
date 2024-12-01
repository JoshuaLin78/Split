package use_cases.home;

/**
 * The interactor for the home use case.
 */
public class HomeInteractor implements HomeInputBoundary {
    private final HomeOutputBoundary userPresenter;

    public HomeInteractor(HomeOutputBoundary homeOutputBoundary) {
        this.userPresenter = homeOutputBoundary;
    }

    /**
     * Confirms the bill details and saves the data.
     * @param homeInputData the input data for write off debt use case
     */
    @Override
    public void execute(HomeInputData homeInputData) {

    }

    /**
     * Executes the switch to new bill view use case
     */
    @Override
    public void switchToNewBillView() {
        userPresenter.switchToNewBillView();
    }

    /**
     * Executes the switch to debtors view use case
     */
    @Override
    public void switchToDebtorsView() {
        userPresenter.switchToDebtorsView();
    }

    /**
     * Executes the switch to history view use case
     */
    @Override
    public void switchToHistoryView() {
        userPresenter.switchToHistoryView();
    }


}

