package hcmute.wepr.ielts_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hcmute.wepr.ielts_app.Utilities.JwtUtils;
import hcmute.wepr.ielts_app.security.annotations.IsAuthenticated;
import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@Controller
@CrossOrigin
@RequestMapping("/auth")
public class TestController {
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping("/login")
	public String test() {
		return "index";
	}
	
	@GetMapping("/login/submit")
	public String login(@RequestParam("username") String username) {
		System.out.println(username);
		return "redirect:/dashboard";
	}
}
