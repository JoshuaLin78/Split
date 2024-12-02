package frameworks;

import use_cases.file_upload.interfaces.OCRProcessorInterface;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GoogleVisionOCRProcessor implements OCRProcessorInterface {

    private static final String CREDENTIALS_PATH = "C:\\Users\\wesle\\Downloads\\ornate-antler-441920-i7-c1f0d0c573ae.json";

    public GoogleVisionOCRProcessor() {
    }

    @Override
    public String processImage(File file){
        try {
            // Load credentials from the JSON key file
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH));

            // Configure the ImageAnnotatorClient with the loaded credentials
            ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials)
                    .build();

            // Create the ImageAnnotatorClient
            try (ImageAnnotatorClient vision = ImageAnnotatorClient.create(settings)) {
                // Read the image file into a ByteString
                ByteString imgBytes = ByteString.readFrom(new FileInputStream(file));

                // Build the image object
                Image img = Image.newBuilder().setContent(imgBytes).build();

                // Set the type of request (TEXT_DETECTION for OCR)
                Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .addFeatures(feat)
                        .setImage(img)
                        .build();

                // Perform the OCR request
                AnnotateImageResponse response = vision.batchAnnotateImages(
                                java.util.Collections.singletonList(request))
                        .getResponses(0);

                if (response.hasError()) {
                    System.out.println("Error: " + response.getError().getMessage());
                    return null;
                }

                // Extract and return the text
                return response.getTextAnnotationsList().get(0).getDescription();
            }

        } catch (IOException e) {
            System.out.println("Failed to process image: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
