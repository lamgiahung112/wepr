package hcmute.wepr.ielts_app.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.enums.Role;
import hcmute.wepr.ielts_app.Services.Interfaces.ForgetPasswordServiceInterface;
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

	@Autowired
	private ForgetPasswordServiceInterface forgetPasswordService;
	
	@PostMapping("/password/{username}")
	@ResponseBody
	public ResponseEntity<?> sendResetEmail(@PathVariable(name = "username", required = true) String username) {
		try {
			forgetPasswordService.sendResetPasswordEmail(username);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/reset/{requestId}")
	public String resetPassword(
			@PathVariable(name= "requestId", required = true) String requestId, 
			@RequestParam(name = "password", required = true) String password, 
			@RequestParam(name = "confirmPassword", required = true) String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return "redirect:/auth/reset?requestId=" + requestId;
		}
		forgetPasswordService.changePassword(requestId, password);
		return "redirect:/auth/student/login";
	}
	
	@GetMapping("/reset")
	public String getResetPasswordPage(Model model, @RequestParam(name = "requestId", required = true) String requestId) {
		if (!forgetPasswordService.isValidRequest(requestId)) {
			return "redirect:/auth/forgetpassword";
		}
		ApplicationUser user = forgetPasswordService.getUserOfRequest(requestId);
		model.addAttribute("requestId", requestId);
		model.addAttribute("user", user);
		return "reset_password";
	}
	
	@GetMapping("/forgetpassword")
	public String getForgetPasswordPage() {
		return "forget_password";
	}
	
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
		String cookieToken = userService.authenticateAndGetCredentials(username, password, Role.ROLE_STUDENT);
		
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
		response.sendRedirect("/home");
	}
	@GetMapping("/student/logout") 
	public String logout(HttpServletResponse response) throws IOException {
		Cookie cookie = new Cookie(COOKIE_NAME, null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setDomain("localhost");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
		return "redirect:/auth/student/login";
	}
}
