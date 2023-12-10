package hcmute.wepr.ielts_app.Controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateCourseRequest;
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
	
	@GetMapping
	@IsTeacher
	public String getCoursesPage(Model model, Authentication authentication) {
		model.addAttribute("teacher_username", authentication.getName());
		return "teacher/teacher-courses";
	}
	
	@GetMapping("/update/{id}")
	@IsTeacher
	public String getUpdateCoursePage(Model model, @PathVariable(name = "id") int courseId) {
		Course course = courseService.findCourseWithLessonsByCourseId(courseId);
		
		model.addAttribute("course", course);
		return "teacher/update_course";
	}
}
