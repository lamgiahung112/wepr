package hcmute.wepr.ielts_app.Controllers.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.security.annotations.IsTeacher;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class TeacherController {
	
	@RequestMapping("/dashboard")
	@IsTeacher
	public String getDashboardPage() {
		return "teacher/dashboard";
	}
}
