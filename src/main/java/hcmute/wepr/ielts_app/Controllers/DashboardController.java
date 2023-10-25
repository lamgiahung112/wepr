package hcmute.wepr.ielts_app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/dashboard")
public class DashboardController {
	@GetMapping
	public String dashboard() {
		return "dashboard";
	}
}
