package licenta.project.Repositories.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageManipulationInterface {
    void uploadGetBytes(MultipartFile image) throws IOException;
}
