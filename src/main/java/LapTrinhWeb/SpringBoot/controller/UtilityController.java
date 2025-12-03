package LapTrinhWeb.SpringBoot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import LapTrinhWeb.SpringBoot.entity.User;
import LapTrinhWeb.SpringBoot.service.UserService;

@Controller
@RequestMapping("/utility")
public class UtilityController {

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/**
	 * Endpoint để hash lại password cho user
	 * Truy cập: /utility/hash-password/{username}/{plainPassword}
	 * Ví dụ: /utility/hash-password/lqk/1234
	 */
	@GetMapping("/hash-password/{username}/{plainPassword}")
	@ResponseBody
	public String hashPassword(@PathVariable String username, @PathVariable String plainPassword) {
		try {
			Optional<User> userOpt = userService.findById(username);
			if (userOpt.isEmpty()) {
				return "User không tồn tại: " + username;
			}
			
			User user = userOpt.get();
			String hashedPassword = passwordEncoder.encode(plainPassword);
			user.setPassword(hashedPassword);
			userService.save(user);
			
			return "Đã hash password cho user: " + username + "<br>Password mới (hashed): " + hashedPassword;
		} catch (Exception e) {
			return "Lỗi: " + e.getMessage();
		}
	}
	
	/**
	 * Endpoint để xem thông tin upload directory
	 * Truy cập: /utility/upload-info
	 */
	@GetMapping("/upload-info")
	@ResponseBody
	public String uploadInfo() {
		String uploadDir = System.getProperty("user.dir") + "/uploads";
		return "Upload directory: " + uploadDir + "<br>" +
		       "Working directory: " + System.getProperty("user.dir") + "<br>" +
		       "Ảnh sẽ được lưu vào thư mục uploads và truy cập qua /uploads/{filename}";
	}
}
