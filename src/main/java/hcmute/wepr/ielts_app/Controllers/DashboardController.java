package hcmute.wepr.ielts_app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.security.annotations.IsAdmin;

@Controller
@CrossOrigin
@RequestMapping("/dashboard")
public class DashboardController {
	@GetMapping
	public String dashboard() {
		return "dashboard";
	}
	@IsAdmin
	@GetMapping("/admin")
	public String adminDashboard() {
		return "secured";
	}
	@GetMapping("/profile")
	public String getProfile() {
		return "student/profile_student";
	}
	@GetMapping("/profile_edit")
	public String getEditProfile() {
		return "student/editprofile_student";
	}
}
