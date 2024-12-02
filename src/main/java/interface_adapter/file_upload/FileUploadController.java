package interface_adapter.file_upload;

import use_cases.file_upload.FileUploadInputBoundary;
import use_cases.file_upload.FileUploadInputData;

public class FileUploadController {
    private final FileUploadInputBoundary fileUploadUseCaseInteractor;

    public FileUploadController(FileUploadInputBoundary fileUploadUseCaseInteractor) {
        this.fileUploadUseCaseInteractor = fileUploadUseCaseInteractor;
    }

    public void execute(String filePath) {
        final FileUploadInputData inputData = new FileUploadInputData(filePath);
        fileUploadUseCaseInteractor.execute(inputData);
    }
}
