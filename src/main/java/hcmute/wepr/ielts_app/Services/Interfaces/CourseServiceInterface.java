package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateCourseRequest;
import hcmute.wepr.ielts_app.Utilities.responses.CourseStatisticsResponse;

public interface CourseServiceInterface {
	Course createNewCourse(CreateNewCourseRequest request);
	Course updateCourse(UpdateCourseRequest request);
	Course findCourseWithLessonsByCourseId(int courseId);
	void rateCourse(RateCourseRequest request);
	CourseStatisticsResponse getCourseStatistics(int courseId);

	void generatesCourses();

	List<Course> getCourseWithSpecAndPaging(String authors, String difficulties, float minPrice, float maxPrice,
			float minRating, float maxRating, Integer minEnrollment, Integer maxEnrollment, String nameSorting,
			String priceSorting, String ratingSorting, Integer itemsPerPage, Integer page);
}
