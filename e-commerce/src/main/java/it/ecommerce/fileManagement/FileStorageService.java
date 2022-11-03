package it.ecommerce.fileManagement;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.ecommerce.exceptions.FileStorageException;
import it.ecommerce.util.Util;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		BufferedImage image, resizedImage = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			if (ImageIO.read(file.getInputStream()) != null) {
				image = ImageIO.read(file.getInputStream());
				try {
					resizedImage = Util.simpleResizeImage(image, 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ImageIO.write(resizedImage, "jpeg", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				Files.copy(is, targetLocation, StandardCopyOption.REPLACE_EXISTING);
			} else {
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			}
			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

}
