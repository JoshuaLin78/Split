package app;

import api.OCRProcessor;
import java.io.File;

public class ImageReader {
    public static void main(String[] args) {
        // Path to the test image file
        String imagePath = "C:\\Users\\joshu\\Downloads\\restaurant-bill.jpg";

        // Create a File object for the image
        File imageFile = new File(imagePath);

        // Verify that the file exists
        if (!imageFile.exists()) {
            System.out.println("Test image file not found at: " + imagePath);
            return;
        }

        // Call the OCRProcessor to process the image
        String extractedText = OCRProcessor.processImage(imageFile);

        // Print the results
        if (extractedText != null) {
            System.out.println("Extracted Text:");
            System.out.println(extractedText);
        } else {
            System.out.println("Failed to extract text from the image.");
        }
    }
}
