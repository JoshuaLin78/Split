package use_cases.file_upload;

public class FileUploadOutputData {
    private final String[][] finalData; // 2D Array

    public FileUploadOutputData(String[][] finalData) {
        this.finalData = finalData;
    }

    public String[][] getFinalData() {
        return finalData;
    }
}

