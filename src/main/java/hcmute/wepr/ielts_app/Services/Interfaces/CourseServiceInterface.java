package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateCourseRequest;
import hcmute.wepr.ielts_app.Utilities.responses.CourseStatisticsResponse;

public interface CourseServiceInterface {
	Course createNewCourse(CreateNewCourseRequest request);
	Course updateCourse(UpdateCourseRequest request);
	Course findCourseWithLessonsByCourseId(int courseId);
	Course findCourseWithLessonsAndWithUserByCourseId(int courseId);
	int getUserCourseRating(int userId, int courseId);
	UserProgress getUserCourseProgress(int userId, int courseId);
	void rateCourse(RateCourseRequest request);
	CourseStatisticsResponse getCourseStatistics(int courseId);

	void generatesCourses();

	List<Course> getCourseWithSpecAndPaging(String authors, String difficulties, boolean priceRangeFilter, float minPrice, float maxPrice,
			boolean ratingRangeFilter, float minRating, float maxRating, Integer minEnrollment, Integer maxEnrollment, String nameSorting,
			String priceSorting, String ratingSorting, Integer itemsPerPage, Integer page);
	Long countCourseWithSpecAndPaging(String authors, String difficulties, boolean priceRangeFilter, Float minPrice,
			Float maxPrice, boolean ratingRangeFilter, Float minRating, Float maxRating, Integer minEnrollment,
			Integer maxEnrollment, String nameSorting, String priceSorting, String ratingSorting, Integer itemsPerPage,
			Integer page);
	Course getCouseWithAllLessons(int courseId);
}
