package hcmute.wepr.ielts_app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/courses")
public class CourseController {
	@GetMapping
	public String coursePage() {
		return "courses";
	}
}
