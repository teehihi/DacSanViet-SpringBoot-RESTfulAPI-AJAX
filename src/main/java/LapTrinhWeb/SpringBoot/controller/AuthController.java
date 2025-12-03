package LapTrinhWeb.SpringBoot.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import LapTrinhWeb.SpringBoot.entity.User;
import LapTrinhWeb.SpringBoot.model.UserModel;
import LapTrinhWeb.SpringBoot.service.UserService;

@Controller
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String register(Model model) {
		UserModel userModel = new UserModel();
		model.addAttribute("user", userModel);
		return "auth/register";
	}

	@PostMapping("/register/save")
	public String registerSave(@ModelAttribute("user") UserModel userModel, 
			RedirectAttributes redirectAttributes, Model model) {
		Optional<User> existingUser = userService.findById(userModel.getUsername());
		if (existingUser.isPresent()) {
			model.addAttribute("error", "Username đã tồn tại! Vui lòng chọn username khác.");
			model.addAttribute("user", userModel);
			return "auth/register";
		}

		User entity = new User();
		BeanUtils.copyProperties(userModel, entity);
		
		if (userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
			entity.setPassword(passwordEncoder.encode(userModel.getPassword()));
		}
		
		entity.setAdmin(false);
		entity.setActive(true);

		userService.save(entity);

		redirectAttributes.addFlashAttribute("message", "Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.");
		return "redirect:/login?success=true";
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "success", required = false) String success,
			Model model) {
		if (error != null) {
			model.addAttribute("error", "Username hoặc password không đúng!");
		}
		if (logout != null) {
			model.addAttribute("message", "Bạn đã đăng xuất thành công!");
		}
		if (success != null) {
			model.addAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
		}
		return "auth/login";
	}
}
