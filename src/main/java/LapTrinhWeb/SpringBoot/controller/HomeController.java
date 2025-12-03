package LapTrinhWeb.SpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import LapTrinhWeb.SpringBoot.service.CategoryService;
import LapTrinhWeb.SpringBoot.service.UserService;
import LapTrinhWeb.SpringBoot.service.VideoService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	VideoService videoService;

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/home")
	public String homePage() {
		return "index";
	}
	
	@GetMapping("/admin")
	public String adminDashboard() {
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/admin/dashboard")
	public String dashboard(Model model) {
		long totalUsers = userService.count();
		long totalCategories = categoryService.count();
		long totalVideos = videoService.count();
		
		model.addAttribute("totalUsers", totalUsers);
		model.addAttribute("totalCategories", totalCategories);
		model.addAttribute("totalVideos", totalVideos);
		
		return "admin/dashboard";
	}
}
