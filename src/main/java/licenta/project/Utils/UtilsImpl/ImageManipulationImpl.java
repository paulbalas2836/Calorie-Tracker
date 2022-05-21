package licenta.project.Utils.UtilsImpl;

import licenta.project.Exceptions.AppException;
import licenta.project.Models.HistoryImage;
import licenta.project.Repositories.HistoryImageRepository;
import licenta.project.Utils.ImageManipulation;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class ImageManipulationImpl implements ImageManipulation {
    private final static Integer IMAGE_HEIGHT = 300;
    private final static Integer IMAGE_WIDTH = 300;
    private final static Integer PROFILE_IMAGE_HEIGHT = 300;
    private final static Integer PROFILE_IMAGE_WIDTH = 300;
    private final HistoryImageRepository historyImageRepository;

    @Override
    public void saveHistoryImage(MultipartFile image, String label, String storingPath, HistoryImage historyImage) {
        try {
            byte[] imageByte = image.getBytes();
            InputStream inputStream = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            BufferedImage resizedImage = Scalr.resize(bufferedImage, IMAGE_WIDTH, IMAGE_HEIGHT);
            String fileName = label + '_' + historyImage.getId() + ".jpg";
            Path imagePath = Paths.get(storingPath, fileName);
            Path filePath = Paths.get(storingPath);
            if (!Files.exists(filePath)) {
                boolean fileCreated = new File(String.valueOf(filePath)).mkdir();
                if (!fileCreated) throw new AppException("Couldn't create file!");
            }

            File newImageFile = imagePath.toFile();
            ImageIO.write(resizedImage, "jpg", newImageFile);
            historyImage.setName(fileName);
            historyImage.setPath(label + '/' + fileName);
            historyImageRepository.save(historyImage);


        } catch (IOException | AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveProfileImage(MultipartFile image, String relativePath) throws IOException {
        String absolutePath = "./src/main/web/public" + relativePath;
        Path uploadPath = Paths.get(absolutePath);

        try {
            byte[] imageByte = image.getBytes();
            InputStream inputStream = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            BufferedImage resizedImage = Scalr.resize(bufferedImage, PROFILE_IMAGE_WIDTH, PROFILE_IMAGE_HEIGHT);
            File newImageFile = uploadPath.toFile();
            ImageIO.write(resizedImage, "png", newImageFile);
        } catch (IOException ioe) {
            throw new IOException("Could not save the image", ioe);
        }
    }
}
