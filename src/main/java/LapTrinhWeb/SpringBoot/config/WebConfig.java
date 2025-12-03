package LapTrinhWeb.SpringBoot.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Serve static images from classpath
		registry.addResourceHandler("/images/**")
				.addResourceLocations("classpath:/static/images/");
		
		// Serve uploaded images from external uploads folder
		String uploadDir = System.getProperty("user.dir") + "/uploads/";
		Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
		String uploadLocation = "file:" + uploadPath.toString() + "/";
		
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations(uploadLocation);
	}
}
