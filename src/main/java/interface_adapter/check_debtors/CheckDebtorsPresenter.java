package interface_adapter.check_debtors;

import interface_adapter.home.HomeViewModel;
import use_cases.check_debtors.CheckDebtorsOutputBoundary;
import interface_adapter.ViewManagerModel;

public class CheckDebtorsPresenter implements CheckDebtorsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final CheckDebtorsViewModel checkDebtorsViewModel;
    private final HomeViewModel homeViewModel; //TEMPORARY, change to home screen later

    public CheckDebtorsPresenter(ViewManagerModel viewManagerModel, CheckDebtorsViewModel checkDebtorsViewModel, HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.checkDebtorsViewModel = checkDebtorsViewModel;
        this.homeViewModel = homeViewModel;
    }

    @Override
    public void prepareSuccessView(CheckDebtorsOutputBoundary checkDebtorsOutputData) {

    }

    @Override
    public void prepareFailureView() {

    }

    @Override
    public void switchView() {
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
