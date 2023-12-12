package hcmute.wepr.ielts_app.Controllers.teacher;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.UserProfile;
import hcmute.wepr.ielts_app.Models.enums.Role;
import hcmute.wepr.ielts_app.Services.Interfaces.TeacherServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpTeacherRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpUserRequest;
import hcmute.wepr.ielts_app.repositories.UserProfileRepositoryInterface;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("/auth/teacher")
public class TeacherAuthenticationController {
	@Value("${auth.cookie.name}")
	private String COOKIE_NAME;
	
	@Autowired
	private UserServiceInterface userService;
	
	@Autowired
	private TeacherServiceInterface teacherService;

	@GetMapping("/login")
	public String getLoginPage() {
		return "teacher/login";
	}
	
	@GetMapping("/signup")
	public String getSignupPage() {
		return "teacher/signup";
	}
	
	@GetMapping("/logout") 
	public String logout(HttpServletResponse response) throws IOException {
		Cookie cookie = new Cookie(COOKIE_NAME, null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setDomain("localhost");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		return "redirect:/auth/teacher/login";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute SignUpTeacherRequest params) {
		ApplicationUser user = userService.createUser(params.getUsername(), params.getPassword(), Role.ROLE_TEACHER, params.getEmail(), 0);
		teacherService.createTeacherProfile(user, params);
		return "redirect:/auth/teacher/login";
	}
	
	@PostMapping("/login")
	public void loginStudent(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletResponse response) throws IOException {
		String cookieToken = userService.authenticateAndGetCredentials(username, password, Role.ROLE_TEACHER);
		
		if (cookieToken == null) {
			response.sendRedirect("/auth/teacher/login");
			return;
		}
		
		Cookie cookie = new Cookie(COOKIE_NAME, cookieToken);
		cookie.setHttpOnly(true);
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		response.sendRedirect("/teacher/dashboard");
	}
}
