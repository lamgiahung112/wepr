package hcmute.wepr.ielts_app.Controllers.teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.WritingExercise;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.responses.CourseStatisticsResponse;
import hcmute.wepr.ielts_app.repositories.WritingExerciseRepositoryInterface;
import hcmute.wepr.ielts_app.security.annotations.IsTeacher;

@Controller
@RequestMapping("/teacher/courses")
@CrossOrigin
public class TeacherCourseController {
	@Autowired
	private CourseServiceInterface courseService;
	@Autowired
	private WritingExerciseRepositoryInterface writingExerciseRepository;
	
	@GetMapping("/new")
	@IsTeacher
	public String getNewCoursePage() {
		return "teacher/new_course";
	}
	
	@GetMapping("/{id}/details")
	@IsTeacher
	public String getCourseDetailsPage(Authentication authentication, Model model,@PathVariable("id") int courseId) {
		Course course = courseService.findCourseWithLessonsByCourseId(courseId);
		List<WritingExercise> exercises = writingExerciseRepository.findAllExercisesByCourseId(courseId);
		course.getLessons().forEach(lesson -> {
			WritingExercise exercise = exercises.stream().filter(e -> e.getLesson().getLessonId() == lesson.getLessonId()).findFirst().orElse(null);
			lesson.setWritingExercise(exercise);
		});
		CourseStatisticsResponse stats = courseService.getCourseStatistics(courseId);
		
		model.addAttribute("course", course);
		model.addAttribute("stats", stats);
		return "teacher/course_details";
	}
	
	@GetMapping
	@IsTeacher
	public String getCoursesPage(Model model, Authentication authentication) {
		model.addAttribute("teacher_username", authentication.getName());
		return "teacher/teacher-courses";
	}
	
	@GetMapping("/{id}/update")
	@IsTeacher
	public String getUpdateCoursePage(Model model, @PathVariable(name = "id") int courseId) {
		Course course = courseService.findCourseWithLessonsByCourseId(courseId);
		
		List<WritingExercise> exercises = writingExerciseRepository.findAllExercisesByCourseId(courseId);
		course.getLessons().forEach(lesson -> {
			WritingExercise exercise = exercises.stream().filter(e -> e.getLesson().getLessonId() == lesson.getLessonId()).findFirst().orElse(null);
			lesson.setWritingExercise(exercise);
		});
		
		model.addAttribute("course", course);
		return "teacher/update_course";
	}
}
