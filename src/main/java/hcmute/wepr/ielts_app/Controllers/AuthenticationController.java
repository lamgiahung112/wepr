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

import hcmute.wepr.ielts_app.Services.Interfaces.StudentServiceInterface;
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
	private StudentServiceInterface studentService;

	@GetMapping("/login/student")
	public String getLoginStudentPage() {
		return "student/login_student";
	}
	
	@GetMapping("/signup/student")
	public String getSignupStudentPage() {
		return "student/signup_student";
	}
	
	@PostMapping("/signup/student")
	public String signupStudent(@ModelAttribute SignUpUserRequest params) {
		studentService.createNewStudent(params);
		return "redirect:/auth/login/student";
	}
	
	@PostMapping("/login/student")
	public void loginStudent(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletResponse response) throws IOException {
		String cookieToken = studentService.authenticateAndGetCredentials(username, password);
		Cookie cookie = new Cookie(COOKIE_NAME, cookieToken);
		cookie.setHttpOnly(true);
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		response.sendRedirect("/dashboard");
	}
	
	///// TEACHER AUTH
	
	@GetMapping("/login/teacher")
	public String getLoginTeacherPage() {
		return "teacher/login_teacher";
	}
	
	@GetMapping("/signup/teacher")
	public String getSignupPage() {
		return "teacher/signup_teacher";
	}
	
	@PostMapping("/signup/teacher")
	public String signupTeacher(@ModelAttribute SignUpUserRequest params) {
		studentService.createNewStudent(params);
		return "redirect:/auth/login/teacher";
	}
	
	@PostMapping("/login/teacher")
	public void loginTeacher(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletResponse response) throws IOException {
		String cookieToken = studentService.authenticateAndGetCredentials(username, password);
		Cookie cookie = new Cookie(COOKIE_NAME, cookieToken);
		cookie.setHttpOnly(true);
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		response.sendRedirect("/dashboard");
	}
}
