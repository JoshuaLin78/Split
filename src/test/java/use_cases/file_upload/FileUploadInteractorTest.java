package use_cases.file_upload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import use_cases.file_upload.interfaces.JsonParserInterface;
import use_cases.file_upload.interfaces.OCRProcessorInterface;
import use_cases.file_upload.interfaces.TextOrganizerInterface;

import java.io.File;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileUploadInteractor.class)
class FileUploadInteractorTest {

    private OCRProcessorInterface ocrProcessor;
    private TextOrganizerInterface textOrganizer;
    private JsonParserInterface jsonParser;
    private FileUploadOutputBoundary outputBoundary;
    private FileUploadInteractor interactor;

    @BeforeEach
    void setUp() {
        ocrProcessor = mock(OCRProcessorInterface.class);
        textOrganizer = mock(TextOrganizerInterface.class);
        jsonParser = mock(JsonParserInterface.class);
        outputBoundary = mock(FileUploadOutputBoundary.class);

        interactor = new FileUploadInteractor(ocrProcessor, textOrganizer, jsonParser, outputBoundary);
    }

    @Test
    void testExecute_fileDoesNotExist() {
        // Mock input data
        String filePath = "path/to/nonexistent/file.jpg";
        FileUploadInputData inputData = new FileUploadInputData(filePath);

        // Mock file behavior
        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(false);

        // Execute the interactor
        interactor.execute(inputData);

        // No success or further processing should occur
        verifyNoInteractions(outputBoundary);
    }

    @Test
    void testExecute_extractedTextIsEmpty() {
        // Mock input data
        String filePath = "path/to/file.jpg";
        FileUploadInputData inputData = new FileUploadInputData(filePath);
        File mockFile = mock(File.class);

        // Mock dependencies
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);
        when(ocrProcessor.processImage(mockFile)).thenReturn("");

        // Execute the interactor
        interactor.execute(inputData);

        // No success or further processing should occur
        verifyNoInteractions(outputBoundary);
    }

    @Test
    void testExecute_organizedJsonIsEmpty() throws Exception {
        // Mock input data
        String filePath = "path/to/file.jpg";
        FileUploadInputData inputData = new FileUploadInputData(filePath);
        File mockFile = mock(File.class);

        // Mock dependencies
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);
        when(ocrProcessor.processImage(mockFile)).thenReturn("Extracted text");
        when(textOrganizer.organizeText("Extracted text")).thenReturn("");

        // Execute the interactor
        interactor.execute(inputData);

        // No success or further processing should occur
        verifyNoInteractions(outputBoundary);
    }

    @Test
    void testExecute_conversionFails() throws Exception {
        // Mock input data
        String filePath = "path/to/file.jpg";
        FileUploadInputData inputData = new FileUploadInputData(filePath);
        File mockFile = mock(File.class);

        // Mock dependencies
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);
        when(ocrProcessor.processImage(mockFile)).thenReturn("Extracted text");
        when(textOrganizer.organizeText("Extracted text")).thenReturn("{\"data\":[]}");
        when(jsonParser.convertJson("{\"data\":[]}")).thenReturn(new String[0][]);

        // Execute the interactor
        interactor.execute(inputData);

        // No success or further processing should occur
        verifyNoInteractions(outputBoundary);
    }

    @Test
    void testExecute_unexpectedError() {
        // Mock input data
        String filePath = "path/to/file.jpg";
        FileUploadInputData inputData = new FileUploadInputData(filePath);
        File mockFile = mock(File.class);

        // Mock dependencies
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);
        when(ocrProcessor.processImage(mockFile)).thenThrow(new RuntimeException("Unexpected error"));

        // Execute the interactor
        interactor.execute(inputData);

        // No success or further processing should occur
        verifyNoInteractions(outputBoundary);
    }
}
