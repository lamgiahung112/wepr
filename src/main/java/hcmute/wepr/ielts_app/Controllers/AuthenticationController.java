package hcmute.wepr.ielts_app.Controllers;

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

import hcmute.wepr.ielts_app.Models.enums.Role;
import hcmute.wepr.ielts_app.Services.Interfaces.StudentServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpUserRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("/auth")
public class AuthenticationController {
	@Value("${auth.cookie.name}")
	private String COOKIE_NAME;
	
	
	@Autowired
	private UserServiceInterface userService;

	@GetMapping("/student/login")
	public String getLoginStudentPage() {
		return "student/login_student";
	}
	
	@GetMapping("/student/signup")
	public String getSignupStudentPage() {
		return "student/signup_student";
	}
	
	@PostMapping("/student/signup")
	public String signupStudent(@ModelAttribute SignUpUserRequest params) {
		userService.createUser(params.getUsername(), params.getPassword(), Role.ROLE_STUDENT, params.getEmail(), 0);
		return "redirect:/auth/student/login";
	}
	
	@PostMapping("/student/login")
	public void loginStudent(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletResponse response) throws IOException {
		String cookieToken = userService.authenticateAndGetCredentials(username, password);
		
		if (cookieToken == null) {
			response.sendRedirect("/auth/student/login");
			return;
		}
		
		Cookie cookie = new Cookie(COOKIE_NAME, cookieToken);
		cookie.setHttpOnly(true);
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		response.sendRedirect("/dashboard");
	}
}
