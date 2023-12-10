package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;

public interface CourseServiceInterface {
	Course createNewCourse(CreateNewCourseRequest request);

	void generatesCourses();

	List<Course> getCourseWithSpecAndPaging(String authors, String difficulties, float minPrice, float maxPrice,
			float minRating, float maxRating, Integer minEnrollment, Integer maxEnrollment, String nameSorting,
			String priceSorting, String ratingSorting, Integer itemsPerPage, Integer page);
}
