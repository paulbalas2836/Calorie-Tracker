package licenta.project.Repositories.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageManipulationInterface {
    String saveImage(MultipartFile image, String label) throws IOException;
}
