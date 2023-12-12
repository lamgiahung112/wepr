package hcmute.wepr.ielts_app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@Controller
@CrossOrigin
@RequestMapping("/exercise")
public class ExerciseController {
	@GetMapping
	@IsStudent
	public String exercisePage() {
		return "student/exercise";
	}
}
