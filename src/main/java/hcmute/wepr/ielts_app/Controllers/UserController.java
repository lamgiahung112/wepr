package hcmute.wepr.ielts_app.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService _userService;
	@GetMapping("/")
	public Optional<ApplicationUser> getSingleUser() {
		return _userService.loadByUsername("tin");
	}
	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}
}
