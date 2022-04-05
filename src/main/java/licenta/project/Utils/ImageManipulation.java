package licenta.project.Utils;

import licenta.project.Repositories.Interfaces.ImageManipulationInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageManipulation implements ImageManipulationInterface {

    @Override
    public void uploadGetBytes(MultipartFile image) {
        try {
            byte[] imageByte = image.getBytes();
            InputStream inputStream = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            String fileName = "Name" + ".jpg";
            Path path = Paths.get("", fileName);
            File newImageFile = path.toFile();
            ImageIO.write(bufferedImage, "jpg", newImageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
