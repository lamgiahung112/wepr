package hcmute.wepr.ielts_app.Controllers.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Services.Interfaces.AdminServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.AdminGetUserListRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.AdminStatisticsRequest;
import hcmute.wepr.ielts_app.Utilities.responses.AdminStatisticsResponse;
import hcmute.wepr.ielts_app.security.annotations.IsAdmin;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
	@Value("${auth.cookie.name}")
	private String COOKIE_NAME;
	
	@Autowired
	private AdminServiceInterface adminService;
	@Autowired
	private UserServiceInterface userService;
	
	/***   PAGE   ***/
	@GetMapping("/login")
	public String getLoginPage() {
		return "admin/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name = "username", required = true) String username,
				@RequestParam(name = "password", required = true) String password,
				HttpServletResponse response
		) throws IOException {
		String cookieToken = userService.authenticateAndGetCredentials(username, password);
		if (cookieToken == null) {
			response.sendRedirect("/auth/student/login");
			return "redirect:/admin/login";
		}
		
		Cookie cookie = new Cookie(COOKIE_NAME, cookieToken);
		cookie.setHttpOnly(true);
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/dashboard")
	@IsAdmin
	public String getDashboardPage() {
		return "admin/dashboard";
	}
	
	/***   APIs   ***/
	@PostMapping("/stats")
	@IsAdmin
	@ResponseBody
	public ResponseEntity<AdminStatisticsResponse> getStats(@RequestBody AdminStatisticsRequest request) {
		return ResponseEntity.ok().body(adminService.getStatistics(request));
	}
	
	@PostMapping("/users")
	@IsAdmin
	@ResponseBody
	public ResponseEntity<List<ApplicationUser>> getStudents(@RequestBody AdminGetUserListRequest request) {
		return ResponseEntity.ok().body(adminService.getUserList(request));
	}
	
	@PostMapping("/users/{userId}/disable")
	@IsAdmin
	@ResponseBody
	public ResponseEntity<?> disableUser(@PathVariable(name = "userId") int userId) {
		adminService.disableUser(userId);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/users/{userId}/enable")
	@IsAdmin
	@ResponseBody
	public ResponseEntity<?> enableUser(@PathVariable(name = "userId") int userId) {
		adminService.enableUser(userId);
		return ResponseEntity.ok().build();
	}
}
