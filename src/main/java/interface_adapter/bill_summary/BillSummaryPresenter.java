package interface_adapter.bill_summary;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_history.ViewHistoryViewModel;
import use_cases.bill_summary.BillSummaryOutputBoundary;

public class BillSummaryPresenter implements BillSummaryOutputBoundary {
    private ViewManagerModel viewManagerModel;
    private ViewHistoryViewModel viewHistoryViewModel;

    public BillSummaryPresenter(ViewManagerModel viewManagerModel, ViewHistoryViewModel viewHistoryViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewHistoryViewModel = viewHistoryViewModel;
    }

    @Override
    public void goBack(){
        viewManagerModel.setState(viewHistoryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
