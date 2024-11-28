package interface_adapter.home;

import interface_adapter.ViewManagerModel;
import interface_adapter.bill_input.BillInputViewModel;
import use_cases.home.HomeOutputBoundary;
import use_cases.home.HomeOutputData;

public class HomePresenter implements HomeOutputBoundary{
    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final BillInputViewModel billInputViewModel;

    public HomePresenter(ViewManagerModel viewManagerModel, HomeViewModel homeViewModel, BillInputViewModel billInputViewModel) {
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.billInputViewModel = billInputViewModel;
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
    }

    /**
     * Switches to the Debtors View
     */
    @Override
    public void switchToDebtorsView() {
        //to be completed when debtors view is implemented
    }

    /**
     * Switches to the History View
     */
    @Override
    public void switchToHistoryView() {
        //to be completed when history view is implemented
    }
}
