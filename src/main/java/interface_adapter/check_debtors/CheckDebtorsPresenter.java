package interface_adapter.check_debtors;

import interface_adapter.bill_input.BillInputViewModel;
import use_cases.check_debtors.CheckDebtorsOutputBoundary;
import interface_adapter.ViewManagerModel;

public class CheckDebtorsPresenter implements CheckDebtorsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final CheckDebtorsViewModel checkDebtorsViewModel;
    private final BillInputViewModel billInputViewModel; //TEMPORARY, change to home screen later

    public CheckDebtorsPresenter(ViewManagerModel viewManagerModel, CheckDebtorsViewModel checkDebtorsViewModel, BillInputViewModel billInputViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.checkDebtorsViewModel = checkDebtorsViewModel;
        this.billInputViewModel = billInputViewModel;
    }

    @Override
    public void prepareSuccessView(CheckDebtorsOutputBoundary checkDebtorsOutputData) {

    }

    @Override
    public void prepareFailureView() {

    }

    @Override
    public void switchView() {
        viewManagerModel.setState(billInputViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
