package hcmute.wepr.ielts_app.Controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateCourseRequest;
import hcmute.wepr.ielts_app.security.annotations.IsTeacher;

@RestController
@CrossOrigin
@RequestMapping("/api/teacher")
public class TeacherApiController {
	@Autowired
	private CourseServiceInterface courseService;
	
	@PostMapping("/course")
	@IsTeacher
	public ResponseEntity<?> createNewCourse(Authentication auth, @RequestBody CreateNewCourseRequest request) {
		Course savedCourse = courseService.createNewCourse(request.setUserId(Integer.parseInt(auth.getCredentials().toString())));
		
		if (savedCourse == null) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/course/update")
	@IsTeacher
	public ResponseEntity<?> updateCourse(@RequestBody UpdateCourseRequest request) {	
		courseService.updateCourse(request);
		return ResponseEntity.ok().build();
	}
}
