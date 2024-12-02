package interface_adapter.write_off_debt;

import interface_adapter.ViewManagerModel;
import interface_adapter.check_debtors.CheckDebtorsState;
import interface_adapter.check_debtors.CheckDebtorsViewModel;
import use_cases.write_off_debt.WriteOffDebtOutputBoundary;
import use_cases.write_off_debt.WriteOffDebtOutputData;

public class WriteOffDebtPresenter implements WriteOffDebtOutputBoundary {
    private final CheckDebtorsViewModel checkDebtorsViewModel;
    private final ViewManagerModel viewManagerModel;

    public WriteOffDebtPresenter(ViewManagerModel viewManagerModel, CheckDebtorsViewModel checkDebtorsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.checkDebtorsViewModel = checkDebtorsViewModel;
    }

    /**
     * Prepares the success view for the WriteOffDebt Use Case
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(WriteOffDebtOutputData outputData) {
        final CheckDebtorsState checkDebtorsState = checkDebtorsViewModel.getState();
        checkDebtorsState.setDebtors(outputData.getDebtors());
        checkDebtorsViewModel.setState(checkDebtorsState);
        checkDebtorsViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the WriteOffDebt Use Case
     * @param errorMessage the message explaining the error
     */
    @Override
    public void prepareFailureView(String errorMessage) {}
}
