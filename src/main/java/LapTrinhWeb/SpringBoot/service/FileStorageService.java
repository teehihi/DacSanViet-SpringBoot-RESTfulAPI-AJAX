package LapTrinhWeb.SpringBoot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	public FileStorageService() {
		// Lưu ảnh vào thư mục uploads bên ngoài project để không cần restart
		String uploadDir = System.getProperty("user.dir") + "/uploads";
		this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
			System.out.println("Upload directory: " + this.fileStorageLocation.toString());
		} catch (Exception ex) {
			throw new RuntimeException("Không thể tạo thư mục lưu file.", ex);
		}
	}

	public String storeFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return null;
		}

		String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if (originalFilename.contains("..")) {
				throw new RuntimeException("Tên file không hợp lệ: " + originalFilename);
			}

			String fileExtension = "";
			int lastDotIndex = originalFilename.lastIndexOf(".");
			if (lastDotIndex > 0) {
				fileExtension = originalFilename.substring(lastDotIndex);
			}
			String newFilename = UUID.randomUUID().toString() + fileExtension;

			Path targetLocation = this.fileStorageLocation.resolve(newFilename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			// Trả về đường dẫn để truy cập qua web
			return newFilename;
		} catch (IOException ex) {
			throw new RuntimeException("Không thể lưu file: " + originalFilename, ex);
		}
	}

	public boolean deleteFile(String filename) {
		if (filename == null || filename.isEmpty()) {
			return false;
		}

		try {
			Path filePath = this.fileStorageLocation.resolve(filename);
			return Files.deleteIfExists(filePath);
		} catch (IOException ex) {
			System.err.println("Lỗi khi xóa file: " + ex.getMessage());
			return false;
		}
	}
}
