package interface_adapter.file_upload;

import interface_adapter.ViewManagerModel;
import interface_adapter.bill_input.BillInputState;
import interface_adapter.bill_input.BillInputViewModel;
import use_cases.file_upload.FileUploadOutputData;
import use_cases.file_upload.FileUploadOutputBoundary;

public class FileUploadPresenter implements FileUploadOutputBoundary {
    private final BillInputViewModel billInputViewModel;
    private final ViewManagerModel viewManagerModel;

    public FileUploadPresenter(ViewManagerModel viewManagerModel, BillInputViewModel billInputViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.billInputViewModel = billInputViewModel;
    }

    @Override
    public void prepareSuccessView(FileUploadOutputData outputData) {
        final BillInputState billInputState = billInputViewModel.getState();
        billInputState.setTableData(outputData.getFinalData());
        billInputViewModel.setState(billInputState);
        billInputViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the WriteOffDebt Use Case
     * @param errorMessage the message explaining the error
     */
    @Override
    public void prepareFailureView(String errorMessage) {}
}
