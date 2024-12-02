package use_cases.file_upload.interfaces;

import java.io.File;

public interface OCRProcessorInterface {
    String processImage(File file);
}
