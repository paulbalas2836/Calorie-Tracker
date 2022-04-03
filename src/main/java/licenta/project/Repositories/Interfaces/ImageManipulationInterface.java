package licenta.project.Repositories.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageManipulationInterface {
    byte[] uploadGetBytes(BufferedImage image) throws IOException;
    byte[] resizeImage(MultipartFile sourceFile);
}
