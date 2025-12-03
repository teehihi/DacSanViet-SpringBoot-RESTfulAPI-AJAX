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

import LapTrinhWeb.SpringBoot.entity.Category;
import LapTrinhWeb.SpringBoot.entity.User;
import LapTrinhWeb.SpringBoot.model.CategoryModel;
import LapTrinhWeb.SpringBoot.service.CategoryService;
import LapTrinhWeb.SpringBoot.service.FileStorageService;
import LapTrinhWeb.SpringBoot.service.UserService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	UserService userService;

	@Autowired
	FileStorageService fileStorageService;

	@GetMapping("/add")
	public String add(Model model) {
		CategoryModel cateModel = new CategoryModel();
		cateModel.setIsEdit(false);
		model.addAttribute("category", cateModel);
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "admin/categories/addOrEdit";
	}

	@GetMapping({"", "/"})
	public String list(Model model,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		try {
			if (search != null && !search.isEmpty()) {
				Pageable pageable = PageRequest.of(page, size);
				Page<Category> pageResult = categoryService.findByCategorynameContaining(search, pageable);
				model.addAttribute("categories", pageResult.getContent());
				model.addAttribute("totalPages", pageResult.getTotalPages());
				model.addAttribute("currentPage", page);
				model.addAttribute("search", search);
			} else {
				List<Category> list = categoryService.findAll();
				model.addAttribute("categories", list);
			}
			return "admin/categories/list";
		} catch (Exception e) {
			model.addAttribute("error", "Lỗi khi tải danh sách danh mục: " + e.getMessage());
			return "admin/categories/list";
		}
	}

	@PostMapping("/saveOrUpdate")
	public String saveOrUpdate(@ModelAttribute("category") CategoryModel cateModel,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			RedirectAttributes redirectAttributes) {

		Category entity;

		try {
			if (cateModel.getIsEdit() != null && cateModel.getIsEdit() && cateModel.getCategoryId() != null) {
				Optional<Category> optCate = categoryService.findById(cateModel.getCategoryId());

				if (optCate.isPresent()) {
					entity = optCate.get();
					String oldImage = entity.getImages();
					BeanUtils.copyProperties(cateModel, entity, "images", "user");

					if (imageFile != null && !imageFile.isEmpty()) {
						if (oldImage != null && !oldImage.isEmpty() && !oldImage.startsWith("http")) {
							fileStorageService.deleteFile(oldImage);
						}
						String newImagePath = fileStorageService.storeFile(imageFile);
						entity.setImages(newImagePath);
					} else {
						if (cateModel.getImages() != null && !cateModel.getImages().isEmpty()) {
							entity.setImages(cateModel.getImages());
						} else {
							entity.setImages(oldImage);
						}
					}
				} else {
					entity = new Category();
					BeanUtils.copyProperties(cateModel, entity, "images", "user");
					if (imageFile != null && !imageFile.isEmpty()) {
						String newImagePath = fileStorageService.storeFile(imageFile);
						entity.setImages(newImagePath);
					} else if (cateModel.getImages() != null) {
						entity.setImages(cateModel.getImages());
					}
				}
			} else {
				entity = new Category();
				BeanUtils.copyProperties(cateModel, entity, "images", "user");
				if (imageFile != null && !imageFile.isEmpty()) {
					String newImagePath = fileStorageService.storeFile(imageFile);
					entity.setImages(newImagePath);
				} else if (cateModel.getImages() != null) {
					entity.setImages(cateModel.getImages());
				}
			}

			if (cateModel.getUsername() != null && !cateModel.getUsername().isEmpty()) {
				Optional<User> optUser = userService.findById(cateModel.getUsername());
				optUser.ifPresent(entity::setUser);
			}

			categoryService.save(entity);

			String message = cateModel.getIsEdit() ? "Cập nhật danh mục thành công!" : "Thêm danh mục thành công!";
			redirectAttributes.addFlashAttribute("message", message);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Lỗi khi xử lý dữ liệu: " + e.getMessage());
		}

		return "redirect:/admin/categories";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Optional<Category> opt = categoryService.findById(id);
		CategoryModel cateModel = new CategoryModel();

		if (opt.isPresent()) {
			Category entity = opt.get();
			BeanUtils.copyProperties(entity, cateModel);

			if (entity.getUser() != null) {
				cateModel.setUsername(entity.getUser().getUsername());
			}

			cateModel.setIsEdit(true);
			model.addAttribute("category", cateModel);
			model.addAttribute("users", userService.findAll());
			return "admin/categories/addOrEdit";
		}

		model.addAttribute("message", "Danh mục không tồn tại!");
		return "redirect:/admin/categories";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		categoryService.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
		return "redirect:/admin/categories";
	}
}
