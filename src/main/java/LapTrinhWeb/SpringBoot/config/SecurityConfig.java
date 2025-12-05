package LapTrinhWeb.SpringBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Cấu hình Spring Security với 2 FilterChain riêng biệt:
 * 1. API Security - Không cần authentication, không tạo session (STATELESS)
 * 2. Web Security - Form login cho admin, có session management
 * 
 * Lợi ích của cách này:
 * - API hoạt động độc lập, không bị ảnh hưởng bởi session/cookie
 * - Performance tốt hơn cho API (không tạo session)
 * - Dễ mở rộng sau này (thêm JWT authentication cho API)
 * - Tách biệt concerns: API security vs Web security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * API Security Configuration
	 * - Áp dụng cho: /api/**, /swagger-ui/**, /v3/api-docs/**
	 * - Không cần authentication (permitAll)
	 * - STATELESS: Không tạo session, không lưu cookie
	 * - Disable CSRF: API không cần CSRF protection
	 * - Order(1): Ưu tiên cao hơn, check trước Web Security
	 */
	@Bean
	@Order(1)
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/api/**", "/swagger-ui/**", "/v3/api-docs/**")
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(csrf -> csrf.disable());
		
		return http.build();
	}

	/**
	 * Web Security Configuration
	 * - Áp dụng cho: Tất cả các URL còn lại
	 * - Form login cho admin
	 * - Session management bình thường
	 * - CSRF disabled (có thể enable nếu cần)
	 * - Order(2): Check sau API Security
	 */
	@Bean
	@Order(2)
	public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/home", "/login", "/register", "/register/save", "/css/**", "/js/**", "/images/**", "/utility/**", "/uploads/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/admin/dashboard", true)
				.failureUrl("/login?error=true")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/?logout=true")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
			)
			.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
}
