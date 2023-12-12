package hcmute.wepr.ielts_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.StudentWritingAnswer;
import hcmute.wepr.ielts_app.Services.Interfaces.StudentWritingAnswerServiceInterface;
import hcmute.wepr.ielts_app.repositories.StudentWritingAnswerRepository;

@Service
public class StudentWritingAnswerService implements StudentWritingAnswerServiceInterface {
	@Autowired
	private StudentWritingAnswerRepository studentWritingAnswerRepository;
	@Override
	public List<StudentWritingAnswer> getAllAnswerAttemptOfUserInAnExercise(int userId, int exerciseId) {
		return studentWritingAnswerRepository.findByUserUserIdAndWritingExerciseExerciseId(userId, exerciseId);
	}
	@Override
	public void createAnswerAttempt(StudentWritingAnswer answerAttempt) {
		studentWritingAnswerRepository.save(answerAttempt);
	}
	@Override
	public List<StudentWritingAnswer> getAllAnswerAttemptWithCorrectionOfUserInAnExercise(int userId, int exerciseId) {
		return studentWritingAnswerRepository.findWithWritingAnswerCorrectionByUserUserIdAndWritingExerciseExerciseId(userId, exerciseId);
	}

}
