package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hcmute.wepr.ielts_app.Models.Course;

public interface CourseRepositoryInterface extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {
	Course findCourseWithLessonsByCourseId(int courseId);
	Course findCourseWithLessonsAndWithUserByCourseId(int courseId);
	Course findCourseWithLessonsWithWritingExerciseByCourseId(int courseId);
}
