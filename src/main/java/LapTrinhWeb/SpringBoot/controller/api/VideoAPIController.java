package LapTrinhWeb.SpringBoot.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import LapTrinhWeb.SpringBoot.entity.Category;
import LapTrinhWeb.SpringBoot.entity.Video;
import LapTrinhWeb.SpringBoot.model.Response;
import LapTrinhWeb.SpringBoot.service.CategoryService;
import LapTrinhWeb.SpringBoot.service.FileStorageService;
import LapTrinhWeb.SpringBoot.service.VideoService;

@RestController
@RequestMapping(path = "/api/video")
@Tag(name = "Video API", description = "API quản lý Video - CRUD operations")
public class VideoAPIController {

	@Autowired
	private VideoService videoService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileStorageService fileStorageService;

	/**
	 * GET /api/video - Lấy tất cả video với phân trang và tìm kiếm
	 * @param title - Tìm kiếm theo tiêu đề (optional)
	 * @param page - Số trang (default: 0)
	 * @param size - Số lượng mỗi trang (default: 10)
	 * @param sort - Sắp xếp theo trường (default: videold)
	 */
	@GetMapping
	public ResponseEntity<?> getAllVideos(
			@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "videold") String sort) {

		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
			Page<Video> videos;

			if (title != null && !title.isEmpty()) {
				videos = videoService.findByTitleContaining(title, pageable);
			} else {
				videos = videoService.findAll(pageable);
			}

			return new ResponseEntity<>(new Response(true, "Lấy danh sách video thành công", videos), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "Lỗi: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * POST /api/video/getVideo - Lấy thông tin video theo ID
	 * @param id - ID của video
	 */
	@PostMapping(path = "/getVideo")
	public ResponseEntity<?> getVideo(@Validated @RequestParam("id") Integer id) {
		Optional<Video> video = videoService.findById(id);

		if (video.isPresent()) {
			return new ResponseEntity<>(new Response(true, "Lấy thông tin video thành công", video.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response(false, "Không tìm thấy video", null), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * POST /api/video/addVideo - Thêm video mới
	 * @param title - Tiêu đề video
	 * @param description - Mô tả video
	 * @param categoryId - ID danh mục
	 * @param poster - File ảnh poster (optional)
	 * @param active - Trạng thái hoạt động (default: true)
	 */
	@Operation(
		summary = "Thêm video mới",
		description = "Tạo video mới với thông tin và upload poster (optional)"
	)
	@PostMapping(path = "/addVideo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addVideo(
			@Parameter(description = "Tiêu đề video", required = true)
			@Validated @RequestParam("title") String title,
			
			@Parameter(description = "Mô tả video")
			@RequestParam(required = false) String description,
			
			@Parameter(description = "ID danh mục", required = true)
			@Validated @RequestParam("categoryId") Integer categoryId,
			
			@Parameter(description = "File ảnh poster", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
			@RequestParam(required = false) MultipartFile poster,
			
			@Parameter(description = "Trạng thái hoạt động")
			@RequestParam(defaultValue = "true") Boolean active) {

		try {
			// Kiểm tra category có tồn tại không
			Optional<Category> category = categoryService.findById(categoryId);
			if (category.isEmpty()) {
				return new ResponseEntity<>(new Response(false, "Không tìm thấy danh mục", null), HttpStatus.BAD_REQUEST);
			}

			Video video = new Video();
			video.setTitle(title);
			video.setDescription(description);
			video.setCategory(category.get());
			video.setActive(active);
			video.setViews(0);

			// Xử lý upload poster
			if (poster != null && !poster.isEmpty()) {
				String filename = fileStorageService.storeFile(poster);
				video.setPoster(filename);
			}

			Video savedVideo = videoService.save(video);
			return new ResponseEntity<>(new Response(true, "Thêm video thành công", savedVideo), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "Lỗi: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * PUT /api/video/updateVideo - Cập nhật video
	 * @param videold - ID của video cần cập nhật
	 * @param title - Tiêu đề video mới
	 * @param description - Mô tả video mới
	 * @param categoryId - ID danh mục mới
	 * @param poster - File ảnh poster mới (optional)
	 * @param active - Trạng thái hoạt động
	 */
	@Operation(
		summary = "Cập nhật video",
		description = "Cập nhật thông tin video và upload poster mới (optional)"
	)
	@PutMapping(path = "/updateVideo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateVideo(
			@Parameter(description = "ID video cần cập nhật", required = true)
			@Validated @RequestParam("videold") Integer videold,
			
			@Parameter(description = "Tiêu đề video mới", required = true)
			@Validated @RequestParam("title") String title,
			
			@Parameter(description = "Mô tả video mới")
			@RequestParam(required = false) String description,
			
			@Parameter(description = "ID danh mục mới", required = true)
			@Validated @RequestParam("categoryId") Integer categoryId,
			
			@Parameter(description = "File ảnh poster mới", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
			@RequestParam(required = false) MultipartFile poster,
			
			@Parameter(description = "Trạng thái hoạt động")
			@RequestParam(defaultValue = "true") Boolean active) {

		try {
			Optional<Video> optVideo = videoService.findById(videold);
			if (optVideo.isEmpty()) {
				return new ResponseEntity<>(new Response(false, "Không tìm thấy video", null), HttpStatus.BAD_REQUEST);
			}

			// Kiểm tra category có tồn tại không
			Optional<Category> category = categoryService.findById(categoryId);
			if (category.isEmpty()) {
				return new ResponseEntity<>(new Response(false, "Không tìm thấy danh mục", null), HttpStatus.BAD_REQUEST);
			}

			Video video = optVideo.get();
			video.setTitle(title);
			video.setDescription(description);
			video.setCategory(category.get());
			video.setActive(active);

			// Xử lý upload poster mới
			if (poster != null && !poster.isEmpty()) {
				// Xóa poster cũ nếu có
				if (video.getPoster() != null && !video.getPoster().isEmpty()) {
					fileStorageService.deleteFile(video.getPoster());
				}
				String filename = fileStorageService.storeFile(poster);
				video.setPoster(filename);
			}

			Video updatedVideo = videoService.save(video);
			return new ResponseEntity<>(new Response(true, "Cập nhật video thành công", updatedVideo), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "Lỗi: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * DELETE /api/video/deleteVideo - Xóa video
	 * @param videold - ID của video cần xóa
	 */
	@DeleteMapping(path = "/deleteVideo")
	public ResponseEntity<?> deleteVideo(@Validated @RequestParam("videold") Integer videold) {
		try {
			Optional<Video> optVideo = videoService.findById(videold);
			if (optVideo.isEmpty()) {
				return new ResponseEntity<>(new Response(false, "Không tìm thấy video", null), HttpStatus.BAD_REQUEST);
			}

			Video video = optVideo.get();
			
			// Xóa poster nếu có
			if (video.getPoster() != null && !video.getPoster().isEmpty()) {
				fileStorageService.deleteFile(video.getPoster());
			}

			videoService.deleteById(videold);
			return new ResponseEntity<>(new Response(true, "Xóa video thành công", video), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "Lỗi: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
