package api;

import java.io.File;

public class ImageReader {

    /**
     * This method processes an image file using the OCRProcessor.
     *
     * @param imagePath The path to the image file to be processed.
     * @return The extracted text from the image, or an error message if processing fails.
     */
    public static String processImageFile(String imagePath) {
        // Create a File object for the image
        File imageFile = new File(imagePath);

        // Verify that the file exists
        if (!imageFile.exists()) {
            return "Error: Image file not found at: " + imagePath;
        }

        // Call the OCRProcessor to process the image
        String extractedText = OCRProcessor.processImage(imageFile);

        // Return the results
        if (extractedText != null) {
            return extractedText;
        } else {
            return "Error: Failed to extract text from the image.";
        }
    }
}
