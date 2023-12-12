package hcmute.wepr.ielts_app.Controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserProgressServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.responses.CourseDTO;
import hcmute.wepr.ielts_app.Utilities.responses.CourseStatisticsResponse;
import hcmute.wepr.ielts_app.Utilities.responses.FilteredCourseResponse;
import hcmute.wepr.ielts_app.Utilities.responses.TeacherNameDTO;
import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@Controller
@CrossOrigin
@RequestMapping("/courses")
public class CourseController {
	@Autowired
	CourseServiceInterface courseService;
	@Autowired
	UserServiceInterface userService;
	@Autowired
	UserProgressServiceInterface userProgressService;
	
	
	
	@IsStudent
	@GetMapping("/{id}/learn")
	public String lessonPage(Authentication auth, Model model, @PathVariable("id") int courseId) {
		int studentId = Integer.valueOf(auth.getCredentials().toString());
		
		UserProgress userProgress = userProgressService.findByUserProgressId(studentId, courseId);
		if (userProgress == null) {
			return "lesson_access_denied";
		}
		
		Course courseWithLessons = courseService.getCouseWithAllLessons(courseId);
		// Sort the lessons by lessonId (as an example)
	    List<Lesson> sortedLessons = courseWithLessons.getLessons().stream()
	            .sorted(Comparator.comparingInt(Lesson::getLessonId))
	            .collect(Collectors.toList());
		model.addAttribute("courseWithLessons", courseWithLessons);
	    model.addAttribute("sortedLessons", sortedLessons);
	    model.addAttribute("userId", studentId);

		return "lessons";
	}
	
	
	@PostMapping("/rating")
	@ResponseBody
	public ResponseEntity<?> rateCourse(@RequestBody RateCourseRequest request) {
		courseService.rateCourse(request);
		
		return ResponseEntity.ok().build();
	}
	
	
	
	@GetMapping("/{courseId}")
	public String getCourseDetail(Authentication authentication, Model model,@PathVariable(name = "courseId") int courseId) {
		Integer userid = Integer.valueOf(authentication.getCredentials().toString());
		System.out.println(userid);
		Course course = courseService.findCourseWithLessonsAndWithUserByCourseId(courseId);
		ApplicationUser author = userService.findWithUserProfileById(course.getUser().getUserId());
		CourseStatisticsResponse stats = courseService.getCourseStatistics(courseId);
		UserProgress progress = null;
		if (userid != null) {
			progress = courseService.getUserCourseProgress(userid, courseId);
		}

		model.addAttribute("course", course);
		model.addAttribute("author", author.getProfile().getName());
		model.addAttribute("stats", stats);
		model.addAttribute("lessons", course.getLessons());
		model.addAttribute("userId", userid);
		model.addAttribute("hasBoughtCourse", progress != null);
		
		return "course_details";
	}
	
	
	
	
}
