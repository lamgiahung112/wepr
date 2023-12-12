package hcmute.wepr.ielts_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hcmute.wepr.ielts_app.Models.WritingExercise;

public interface WritingExerciseRepositoryInterface extends JpaRepository<WritingExercise, Integer> {
	@Query("SELECT e from WritingExercise e "
			+ "JOIN Lesson l on l = e.lesson "
			+ "JOIN Course c on c = l.course "
			+ "where c.courseId = :courseId")
    List<WritingExercise> findAllExercisesByCourseId(@Param("courseId") int courseId);
}
