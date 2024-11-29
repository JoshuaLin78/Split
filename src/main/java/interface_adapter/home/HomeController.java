package interface_adapter.home;

import entity.Debtor;
import use_cases.home.HomeInputBoundary;
import use_cases.home.HomeInputData;

public class HomeController {
    private final HomeInputBoundary userHomeUseCaseInteractor;

    public HomeController(HomeInputBoundary userHomeUseCaseInteractor) {
        this.userHomeUseCaseInteractor = userHomeUseCaseInteractor;
    }

    public void execute(Debtor debtor, double amount) {
        final HomeInputData homeInputData = new HomeInputData(debtor, amount);

        userHomeUseCaseInteractor.execute(homeInputData);
    }

    public void switchToNewBillView() {
        userHomeUseCaseInteractor.switchToNewBillView();
    }

    public void switchToDebtorsView() {
        userHomeUseCaseInteractor.switchToDebtorsView();
    }

    public void switchToHistoryView() {
        userHomeUseCaseInteractor.switchToHistoryView();
    }
}
