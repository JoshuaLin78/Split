package interface_adapter.write_off_debt;

import interface_adapter.ViewManagerModel;
import interface_adapter.bill_input.BillInputViewModel;
import use_cases.write_off_debt.WriteOffDebtOutputBoundary;
import use_cases.write_off_debt.WriteOffDebtOutputData;

public class WriteOffDebtPresenter implements WriteOffDebtOutputBoundary{
    private final WriteOffDebtViewModel writeOffDebtViewModel;
    private final ViewManagerModel viewManagerModel;
    private final BillInputViewModel billInputViewModel;

    public WriteOffDebtPresenter(ViewManagerModel viewManagerModel, WriteOffDebtViewModel writeOffDebtViewModel, BillInputViewModel billInputViewModel) {
        this.writeOffDebtViewModel = writeOffDebtViewModel;
        this.viewManagerModel = viewManagerModel;
        this.billInputViewModel = billInputViewModel;
    }

    /**
     * Prepares the success view for the WriteOffDebt Use Case
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(WriteOffDebtOutputData outputData) {

    }

    /**
     * Prepares the failure view for the WriteOffDebt Use Case
     * @param errorMessage the message explaining the error
     */
    @Override
    public void prepareFailureView(String errorMessage) {}
}
