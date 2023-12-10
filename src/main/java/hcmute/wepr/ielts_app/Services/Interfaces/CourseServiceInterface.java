package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateCourseRequest;

public interface CourseServiceInterface {
	Course createNewCourse(CreateNewCourseRequest request);
	Course updateCourse(UpdateCourseRequest request);
	Course findCourseWithLessonsByCourseId(int courseId);

	void generatesCourses();

	List<Course> getCourseWithSpecAndPaging(String authors, String difficulties, boolean priceRangeFilter, float minPrice, float maxPrice,
			boolean ratingRangeFilter, float minRating, float maxRating, Integer minEnrollment, Integer maxEnrollment, String nameSorting,
			String priceSorting, String ratingSorting, Integer itemsPerPage, Integer page);
}
