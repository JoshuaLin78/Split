package use_cases.file_upload;

import use_cases.bill_input.BillInputOutputData;
import use_cases.file_upload.interfaces.JsonParserInterface;
import use_cases.file_upload.interfaces.OCRProcessorInterface;
import use_cases.file_upload.interfaces.TextOrganizerInterface;

import java.io.File;

public class FileUploadInteractor implements FileUploadInputBoundary {
    private final OCRProcessorInterface ocrProcessor;
    private final TextOrganizerInterface textOrganizer;
    private final JsonParserInterface jsonParser;
    private final FileUploadOutputBoundary userPresenter;

    public FileUploadInteractor(
            OCRProcessorInterface ocrProcessor,
            TextOrganizerInterface textOrganizer,
            JsonParserInterface jsonParser,
            FileUploadOutputBoundary fileUploadOutputData) {
        this.ocrProcessor = ocrProcessor;
        this.textOrganizer = textOrganizer;
        this.jsonParser = jsonParser;
        this.userPresenter = fileUploadOutputData;
    }

    @Override
    public void execute(FileUploadInputData inputData) {
        String filePath = inputData.getFilePath();
        File file = new File(filePath);

        // Step 1: Validate file existence
        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File does not exist or is not valid at path: " + filePath);
            return;
        }

        try {
            // Step 2: Extract text using OCRProcessor
            String extractedText = ocrProcessor.processImage(file);
            if (extractedText == null || extractedText.isEmpty()) {
                System.out.println("Error: Failed to extract text from the image.");
                return;
            }

            // Step 3: Organize text into JSON using TextOrganizer
            String organizedJson = textOrganizer.organizeText(extractedText);
            if (organizedJson == null || organizedJson.isEmpty()) {
                System.out.println("Error: Failed to organize text into JSON.");
                return;
            }

            // Step 4: Convert JSON to 2D array using JsonParser
            String[][] finalData = jsonParser.convertJson(organizedJson);
            if (finalData == null || finalData.length == 0) {
                System.out.println("Error: Failed to convert JSON to 2D array.");
                return;
            }
            final FileUploadOutputData fileUploadOutputData = new FileUploadOutputData(finalData);
            userPresenter.prepareSuccessView(fileUploadOutputData);

        } catch (Exception e) {
            System.out.println("Unexpected error during file processing: " + e.getMessage());
        }
    }
}
