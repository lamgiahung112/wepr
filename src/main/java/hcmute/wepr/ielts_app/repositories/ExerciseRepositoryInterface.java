package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.WritingExercise;

public interface ExerciseRepositoryInterface extends JpaRepository<WritingExercise, Integer> {
	WritingExercise findByLessonLessonId(int lessonId);
}
