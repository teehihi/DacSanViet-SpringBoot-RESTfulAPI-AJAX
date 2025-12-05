package LapTrinhWeb.SpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import LapTrinhWeb.SpringBoot.service.CategoryService;

@Controller
@RequestMapping("/admin/videos-ajax")
public class VideoAjaxController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public String videosAjaxPage(Model model) {
		// Truyền danh sách categories để dùng trong form
		model.addAttribute("categories", categoryService.findAll());
		return "admin/videos/ajax-list";
	}
}
