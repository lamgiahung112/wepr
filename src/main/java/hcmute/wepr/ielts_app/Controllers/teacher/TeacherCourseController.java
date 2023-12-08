package hcmute.wepr.ielts_app.Controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.security.annotations.IsTeacher;

@Controller
@RequestMapping("/teacher/courses")
@CrossOrigin
public class TeacherCourseController {
	@Autowired
	private CourseServiceInterface courseService;
	
	@GetMapping("/new")
	@IsTeacher
	public String getNewCoursePage() {
		return "teacher/new_course";
	}
	
	@PostMapping("/new")
	@IsTeacher
	public String addNewCourse(@RequestBody CreateNewCourseRequest request) throws Exception {
		Course savedCourse = courseService.createNewCourse(request);
		
		if (savedCourse == null) {
			throw new Exception();
		}
		return "teacher/new_course";
	}
}
