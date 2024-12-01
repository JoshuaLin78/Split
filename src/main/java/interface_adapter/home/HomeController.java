package interface_adapter.home;

import use_cases.home.HomeInputBoundary;

/**
 * Controller for the Home View.
 */
public class HomeController {
    private final HomeInputBoundary userHomeUseCaseInteractor;

    public HomeController(HomeInputBoundary userHomeUseCaseInteractor) {
        this.userHomeUseCaseInteractor = userHomeUseCaseInteractor;
    }

    /**
     * Switches to the bill input view.
     */
    public void switchToNewBillView() {
        userHomeUseCaseInteractor.switchToNewBillView();
    }

    /**
     * Switches to the check debtors view.
     */
    public void switchToDebtorsView() {
        userHomeUseCaseInteractor.switchToDebtorsView();
    }

    /**
     * Switches to the view history view.
     */
    public void switchToHistoryView() {
        userHomeUseCaseInteractor.switchToHistoryView();
    }
}
