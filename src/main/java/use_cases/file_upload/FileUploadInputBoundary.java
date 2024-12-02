package use_cases.file_upload;

public interface FileUploadInputBoundary {

    /**
     * Executes the file upload use case
     * @param fileUploadInputData the input data
     */
    void execute(FileUploadInputData fileUploadInputData);
}

