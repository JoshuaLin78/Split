package interface_adapter.home;

import interface_adapter.ViewManagerModel;
import interface_adapter.bill_input.BillInputViewModel;
import interface_adapter.check_debtors.CheckDebtorsViewModel;
import interface_adapter.view_history.ViewHistoryViewModel;
import use_cases.home.HomeOutputBoundary;
import use_cases.home.HomeOutputData;
import view.ViewHistoryView;

public class HomePresenter implements HomeOutputBoundary{
    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final BillInputViewModel billInputViewModel;
    private final CheckDebtorsViewModel checkDebtorsViewModel;
    private final ViewHistoryViewModel viewHistoryViewModel;

    public HomePresenter(ViewManagerModel viewManagerModel, HomeViewModel homeViewModel,
                         BillInputViewModel billInputViewModel, CheckDebtorsViewModel checkDebtorsViewModel, ViewHistoryViewModel viewHistoryViewModel) {
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.billInputViewModel = billInputViewModel;
        this.checkDebtorsViewModel = checkDebtorsViewModel;
        this.viewHistoryViewModel = viewHistoryViewModel;
    }

    /**
     * Prepares the success view for the Home Use Case
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(HomeOutputData outputData) {

    }

    /**
     * Prepares the failure view for the Home Use Case
     * @param errorMessage the message explaining the error
     */
    @Override
    public void prepareFailureView(String errorMessage) {}

    /**
     * Switches to the Bill Input View
     */
    @Override
    public void switchToNewBillView() {
        viewManagerModel.setState(billInputViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Switches to the Debtors View
     */
    @Override
    public void switchToDebtorsView() {
        viewManagerModel.setState(checkDebtorsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Switches to the History View
     */
    @Override
    public void switchToHistoryView() {
        viewManagerModel.setState(viewHistoryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
