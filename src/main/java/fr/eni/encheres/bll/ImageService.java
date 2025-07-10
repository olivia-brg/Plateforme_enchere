package fr.eni.encheres.bll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private static final String UPLOAD_DIR = "src/main/resources/static/img/";

    public String saveImage(MultipartFile file) throws IOException {
        logger.info("Image name dans le service: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Le fichier doit être une image");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String filename = "img_" + uuid + extension;

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        logger.info("Image sauvegardée: {}", filename);
        return filename;
    }


    public void deleteImage(String filename) {
        if (filename != null && !filename.isEmpty()) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR + filename);
                Files.deleteIfExists(filePath);
                logger.info("Image supprimée: {}", filename);
            } catch (IOException e) {
                logger.error("Erreur lors de la suppression de l'image: {}", filename, e);
            }
        }
    }
}