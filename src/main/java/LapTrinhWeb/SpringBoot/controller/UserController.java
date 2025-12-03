package LapTrinhWeb.SpringBoot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import LapTrinhWeb.SpringBoot.entity.User;
import LapTrinhWeb.SpringBoot.model.UserModel;
import LapTrinhWeb.SpringBoot.service.FileStorageService;
import LapTrinhWeb.SpringBoot.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	FileStorageService fileStorageService;
	
	@Autowired
	org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

	@GetMapping("/add")
	public String add(Model model) {
		UserModel userModel = new UserModel();
		userModel.setIsEdit(false);
		model.addAttribute("user", userModel);
		return "admin/users/addOrEdit";
	}

	@GetMapping({"", "/"})
	public String list(Model model,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		try {
			if (search != null && !search.isEmpty()) {
				Pageable pageable = PageRequest.of(page, size);
				Page<User> pageResult = userService.findByFullnameContaining(search, pageable);
				model.addAttribute("users", pageResult.getContent());
				model.addAttribute("totalPages", pageResult.getTotalPages());
				model.addAttribute("currentPage", page);
				model.addAttribute("search", search);
			} else {
				List<User> list = userService.findAll();
				model.addAttribute("users", list);
			}
			return "admin/users/list";
		} catch (Exception e) {
			model.addAttribute("error", "Lỗi khi tải danh sách người dùng: " + e.getMessage());
			return "admin/users/list";
		}
	}

	@PostMapping("/saveOrUpdate")
	public String saveOrUpdate(@ModelAttribute("user") UserModel userModel,
			@RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
			RedirectAttributes redirectAttributes) {
		User entity;
		
		try {
			if (userModel.getIsEdit() != null && userModel.getIsEdit() && userModel.getUsername() != null) {
				// Edit existing user
				Optional<User> existingUser = userService.findById(userModel.getUsername());
				if (existingUser.isPresent()) {
					entity = existingUser.get();
					String oldPassword = entity.getPassword();
					String oldImage = entity.getImages();
					
					// Copy properties except password and images
					entity.setFullname(userModel.getFullname());
					entity.setEmail(userModel.getEmail());
					entity.setPhone(userModel.getPhone());
					entity.setAdmin(userModel.getAdmin() != null ? userModel.getAdmin() : false);
					entity.setActive(userModel.getActive() != null ? userModel.getActive() : true);
					
					// Handle image
					if (avatarFile != null && !avatarFile.isEmpty()) {
						if (oldImage != null && !oldImage.isEmpty() && !oldImage.startsWith("http")) {
							try {
								fileStorageService.deleteFile(oldImage);
							} catch (Exception e) {
								// Ignore delete error
							}
						}
						String newImagePath = fileStorageService.storeFile(avatarFile);
						entity.setImages(newImagePath);
					} else if (userModel.getImages() != null && !userModel.getImages().isEmpty()) {
						entity.setImages(userModel.getImages());
					} else {
						entity.setImages(oldImage);
					}
					
					// Handle password - only update if provided
					if (userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
						entity.setPassword(passwordEncoder.encode(userModel.getPassword()));
					} else {
						entity.setPassword(oldPassword);
					}
				} else {
					redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại!");
					return "redirect:/admin/users";
				}
			} else {
				// Create new user
				entity = new User();
				entity.setUsername(userModel.getUsername());
				entity.setFullname(userModel.getFullname());
				entity.setEmail(userModel.getEmail());
				entity.setPhone(userModel.getPhone());
				entity.setAdmin(userModel.getAdmin() != null ? userModel.getAdmin() : false);
				entity.setActive(userModel.getActive() != null ? userModel.getActive() : true);
				
				// Handle image
				if (avatarFile != null && !avatarFile.isEmpty()) {
					String newImagePath = fileStorageService.storeFile(avatarFile);
					entity.setImages(newImagePath);
				} else if (userModel.getImages() != null && !userModel.getImages().isEmpty()) {
					entity.setImages(userModel.getImages());
				}
				
				// Hash password for new user
				if (userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
					entity.setPassword(passwordEncoder.encode(userModel.getPassword()));
				} else {
					redirectAttributes.addFlashAttribute("error", "Mật khẩu không được để trống!");
					return "redirect:/admin/users/add";
				}
			}

			userService.save(entity);

			String message = (userModel.getIsEdit() != null && userModel.getIsEdit()) 
				? "Cập nhật người dùng thành công!" : "Thêm người dùng thành công!";
			redirectAttributes.addFlashAttribute("message", message);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Lỗi khi xử lý dữ liệu: " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/admin/users";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") String id) {
		Optional<User> optUser = userService.findById(id);
		UserModel userModel = new UserModel();

		if (optUser.isPresent()) {
			User entity = optUser.get();
			BeanUtils.copyProperties(entity, userModel);
			userModel.setIsEdit(true);
			model.addAttribute("user", userModel);
			return "admin/users/addOrEdit";
		}

		model.addAttribute("message", "Người dùng không tồn tại!");
		return "redirect:/admin/users";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		userService.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Xóa người dùng thành công!");
		return "redirect:/admin/users";
	}
}
