package licenta.project.Utils;

import licenta.project.Exceptions.AppException;
import licenta.project.Models.HistoryImage;
import licenta.project.Repositories.HistoryImageRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
public class ImageManipulation implements licenta.project.Repositories.Interfaces.ImageManipulation {
    private final static Integer IMAGE_HEIGHT = 300;
    private final static Integer IMAGE_WIDTH = 300;
    @Autowired
    private HistoryImageRepository historyImageRepository;
    @Override
    public String saveImage(MultipartFile image, String label, String storingPath, HistoryImage historyImage) {
        try {
            byte[] imageByte = image.getBytes();
            InputStream inputStream = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            BufferedImage resizedImage = Scalr.resize(bufferedImage,IMAGE_WIDTH,IMAGE_HEIGHT);
            String fileName = label + '_' + historyImage.getId();
            Path imagePath = Paths.get(storingPath, fileName+".jpg");
            Path filePath = Paths.get(storingPath);
            if(!Files.exists(filePath))
            {
              boolean fileCreated = new File(String.valueOf(filePath)).mkdir();
              if(!fileCreated) throw new AppException("Couldn't create file!");
            }

            File newImageFile = imagePath.toFile();
            ImageIO.write(resizedImage, "jpg", newImageFile);
            historyImage.setName(fileName);
            historyImage.setPath(String.valueOf(imagePath));
            historyImageRepository.save(historyImage);

            return fileName;

        } catch (IOException | AppException e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
