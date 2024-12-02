package use_cases.file_upload;

public class FileUploadOutputData {
    private final String[][] finalData; // 2D Array
    private final String organizedJson; // JSON output from OrganizeText
    private final String extractedText; // Raw text from OCRProcessor
    private final boolean success;
    private final String errorMessage;

    public FileUploadOutputData(String[][] finalData, String organizedJson, String extractedText) {
        this.finalData = finalData;
        this.organizedJson = organizedJson;
        this.extractedText = extractedText;
        this.success = true;
        this.errorMessage = null;
    }

    public FileUploadOutputData(String errorMessage) {
        this.finalData = null;
        this.organizedJson = null;
        this.extractedText = null;
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public String[][] getFinalData() {
        return finalData;
    }

    public String getOrganizedJson() {
        return organizedJson;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

