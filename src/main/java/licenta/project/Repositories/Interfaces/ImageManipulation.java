package licenta.project.Repositories.Interfaces;

import licenta.project.Models.HistoryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageManipulation {
    String saveImage(MultipartFile image, String label, String storingPath, HistoryImage historyImage) throws IOException;
}
