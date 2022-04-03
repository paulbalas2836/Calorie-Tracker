package licenta.project.Utils;

import licenta.project.Repositories.Interfaces.ImageManipulationInterface;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageManipulation implements ImageManipulationInterface {
    private static final Integer SIZE = 64;

    @Override
    public byte[] uploadGetBytes(BufferedImage image) throws IOException {
        String fileName = "Name" + ".jpg";
        Path path = Paths.get("",fileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }


    @Override
    public byte[] resizeImage(MultipartFile image) {
        try{
            byte [] imageByte=image.getBytes();
            InputStream inputStream = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return uploadGetBytes(Scalr.resize(bufferedImage, SIZE));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
