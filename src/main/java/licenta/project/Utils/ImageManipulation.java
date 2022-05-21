package licenta.project.Utils;

import licenta.project.Models.HistoryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageManipulation {
    void saveHistoryImage(MultipartFile image, String label, String storingPath, HistoryImage historyImage) throws IOException;
    void saveProfileImage(MultipartFile image, String storingPath) throws IOException;
}
