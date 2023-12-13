package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.StudentWritingAnswer;

public interface StudentWritingAnswerServiceInterface {
	List<StudentWritingAnswer> getAllAnswerAttemptOfUserInAnExercise(int userId, int exerciseId);
	List<StudentWritingAnswer> getAllAnswerAttemptWithCorrectionOfUserInAnExercise(int userId, int exerciseId);
	void createAnswerAttempt(StudentWritingAnswer answerAttempt);
	List<StudentWritingAnswer> getAllAnswerAttemptOfAnExercise(int exerciseId);
	StudentWritingAnswer getAnswerWithExerciseAndCorrection(int answerId);
}
