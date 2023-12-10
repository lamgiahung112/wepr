package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;

public interface CourseServiceInterface {
	Course createNewCourse(CreateNewCourseRequest request);
}
