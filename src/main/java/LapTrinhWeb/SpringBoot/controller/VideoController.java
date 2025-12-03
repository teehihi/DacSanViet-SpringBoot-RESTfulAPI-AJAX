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
import LapTrinhWeb.SpringBoot.entity.Video;
import LapTrinhWeb.SpringBoot.model.VideoModel;
import LapTrinhWeb.SpringBoot.service.CategoryService;
import LapTrinhWeb.SpringBoot.service.FileStorageService;
import LapTrinhWeb.SpringBoot.service.VideoService;

@Controller
@RequestMapping("/admin/videos")
public class VideoController {

	@Autowired
	VideoService videoService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	FileStorageService fileStorageService;

	@GetMapping("/add")
	public String add(Model model) {
		VideoModel videoModel = new VideoModel();
		videoModel.setIsEdit(false);
		model.addAttribute("video", videoModel);
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		return "admin/videos/addOrEdit";
	}

	@GetMapping({"", "/"})
	public String list(Model model, 
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		try {
			if (search != null && !search.isEmpty()) {
				Pageable pageable = PageRequest.of(page, size);
				Page<Video> pageResult = videoService.findByTitleContaining(search, pageable);
				model.addAttribute("videos", pageResult.getContent());
				model.addAttribute("totalPages", pageResult.getTotalPages());
				model.addAttribute("currentPage", page);
				model.addAttribute("search", search);
			} else {
				List<Video> list = videoService.findAll();
				model.addAttribute("videos", list);
			}
			return "admin/videos/list";
		} catch (Exception e) {
			model.addAttribute("error", "Lỗi khi tải danh sách video: " + e.getMessage());
			return "admin/videos/list";
		}
	}

	@PostMapping("/saveOrUpdate")
	public String saveOrUpdate(@ModelAttribute("video") VideoModel videoModel,
			@RequestParam(value = "posterFile", required = false) MultipartFile posterFile,
			RedirectAttributes redirectAttributes) {
		Video entity;
		
		try {
			if (videoModel.getIsEdit() != null && videoModel.getIsEdit() && videoModel.getVideold() != null) {
				Optional<Video> existingVideo = videoService.findById(videoModel.getVideold());
				if (existingVideo.isPresent()) {
					entity = existingVideo.get();
				} else {
					entity = new Video();
				}
			} else {
				entity = new Video();
			}
			
			if (videoModel.getTitle() != null) {
				entity.setTitle(videoModel.getTitle());
			}
			if (videoModel.getViews() != null) {
				entity.setViews(videoModel.getViews());
			}
			if (videoModel.getDescription() != null) {
				entity.setDescription(videoModel.getDescription());
			}
			if (videoModel.getActive() != null) {
				entity.setActive(videoModel.getActive());
			}
			
			String oldPoster = entity.getPoster();
			if (posterFile != null && !posterFile.isEmpty()) {
				if (oldPoster != null && !oldPoster.isEmpty() && !oldPoster.startsWith("http")) {
					fileStorageService.deleteFile(oldPoster);
				}
				String newPosterPath = fileStorageService.storeFile(posterFile);
				entity.setPoster(newPosterPath);
			} else {
				if (videoModel.getPoster() != null && !videoModel.getPoster().isEmpty()) {
					entity.setPoster(videoModel.getPoster());
				} else if (oldPoster != null) {
					entity.setPoster(oldPoster);
				}
			}

			if (videoModel.getCategoryId() != null) {
				Optional<Category> optCategory = categoryService.findById(videoModel.getCategoryId());
				if (optCategory.isPresent()) {
					entity.setCategory(optCategory.get());
				}
			}

			videoService.save(entity);

			String message = (videoModel.getIsEdit() != null && videoModel.getIsEdit()) 
				? "Cập nhật video thành công!" : "Thêm video thành công!";
			redirectAttributes.addFlashAttribute("message", message);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Lỗi khi xử lý dữ liệu: " + e.getMessage());
		}

		return "redirect:/admin/videos";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Optional<Video> optVideo = videoService.findById(id);
		VideoModel videoModel = new VideoModel();

		if (optVideo.isPresent()) {
			Video entity = optVideo.get();
			BeanUtils.copyProperties(entity, videoModel);
			if (entity.getCategory() != null) {
				videoModel.setCategoryId(entity.getCategory().getCategoryId());
			}
			videoModel.setIsEdit(true);
			model.addAttribute("video", videoModel);
			List<Category> categories = categoryService.findAll();
			model.addAttribute("categories", categories);
			return "admin/videos/addOrEdit";
		}

		model.addAttribute("message", "Video không tồn tại!");
		return "redirect:/admin/videos";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		videoService.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Xóa video thành công!");
		return "redirect:/admin/videos";
	}
}
