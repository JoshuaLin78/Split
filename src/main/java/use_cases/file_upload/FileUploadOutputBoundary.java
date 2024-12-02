package use_cases.file_upload;

public interface FileUploadOutputBoundary {
    /**
     * Prepares the success view for the FileUpload Use Case
     * @param outputData the output data
     */
    void prepareSuccessView(FileUploadOutputData outputData);

    /**
     * Prepares the failure view for the FileUpload Use Case
     * @param errorMessage the message explaining the error
     */
    void prepareFailureView(String errorMessage);
}

