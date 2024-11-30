package interface_adapter.view_history;

import entity.OrderSummary;
import interface_adapter.ViewManagerModel;
import interface_adapter.bill_summary.BillSummaryState;
import interface_adapter.bill_summary.BillSummaryViewModel;
import interface_adapter.check_debtors.CheckDebtorsState;
import interface_adapter.home.HomeViewModel;
import use_cases.view_history.ViewHistoryOutputBoundary;
import use_cases.view_history.ViewHistoryOutputData;

public class ViewHistoryPresenter implements ViewHistoryOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;
    private final BillSummaryViewModel billSummaryViewModel;

    public ViewHistoryPresenter(ViewManagerModel viewManagerModel, HomeViewModel homeViewModel, BillSummaryViewModel billSummaryViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = homeViewModel;
        this.billSummaryViewModel = billSummaryViewModel;
    }

    @Override
    public void orderView(ViewHistoryOutputData outputData){
        final BillSummaryState billSummaryState = billSummaryViewModel.getState();
        billSummaryState.setOrderSummary(outputData.getOrderSummary());
        this.billSummaryViewModel.setState(billSummaryState);
        billSummaryViewModel.firePropertyChanged();

        viewManagerModel.setState(billSummaryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Switches the view back to the homepage.
     */
    @Override
    public void returnHome(){
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
