package hcmute.wepr.ielts_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.StudentWritingAnswer;

public interface StudentWritingAnswerRepository extends JpaRepository<StudentWritingAnswer, Integer> {
	List<StudentWritingAnswer> findByUserUserIdAndWritingExerciseExerciseId(int userId, int writingExerciseId);
	List<StudentWritingAnswer> findWithWritingAnswerCorrectionByUserUserIdAndWritingExerciseExerciseId(int userId, int writingExerciseId);
}
