package hcmute.wepr.ielts_app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.security.annotations.IsAuthenticated;
import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@Controller
@CrossOrigin
@RequestMapping("/auth")
public class TestController {
	
	@GetMapping("/login")
	public String test() {
		return "index";
	}
	
}
