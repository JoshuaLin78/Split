package use_cases.file_upload;

public class FileUploadInputData {
    private final String filePath;

    public FileUploadInputData(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
