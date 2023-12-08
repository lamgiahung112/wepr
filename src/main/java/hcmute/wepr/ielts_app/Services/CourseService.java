package hcmute.wepr.ielts_app.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.repositories.CourseRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.LessonRepositoryInterface;

@Service
public class CourseService implements CourseServiceInterface {
	@Autowired
	private CourseRepositoryInterface courseRepository;
	@Autowired
	private LessonRepositoryInterface lessonRepository;
	
	@Override
	public Course createNewCourse(CreateNewCourseRequest request) {
		Course course = Course.builder()
				.courseName(request.getCourseName())
				.description(request.getCourseDescription())
				.coverImage(request.getCoverImageLink())
				.level(request.getDifficultyLevel())
				.price(request.getPrice())
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		
		Course savedCourse = courseRepository.save(course);
		if (savedCourse == null) return null;
		
		request.getLessons().stream().forEach(
				lesson -> {
					Lesson toBeSavedLesson = Lesson.builder()
							.description(lesson.getDescription())
							.title(lesson.getTitle())
							.video(lesson.getVideoLink())
							.course(savedCourse)
							.build();
					lessonRepository.save(toBeSavedLesson);
				}
		);
		return savedCourse;
	}

}
