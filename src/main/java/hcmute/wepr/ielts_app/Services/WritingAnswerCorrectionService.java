package hcmute.wepr.ielts_app.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.StudentWritingAnswer;
import hcmute.wepr.ielts_app.Models.WritingAnswerCorrection;
import hcmute.wepr.ielts_app.Models.enums.StudentAnswerStatus;
import hcmute.wepr.ielts_app.Services.Interfaces.WritingAnswerCorrectionServiceInterface;
import hcmute.wepr.ielts_app.repositories.StudentWritingAnswerRepository;
import hcmute.wepr.ielts_app.repositories.WritingAnswerCorrectionRepositoryInterface;

@Service
public class WritingAnswerCorrectionService implements WritingAnswerCorrectionServiceInterface {

	@Autowired
	private WritingAnswerCorrectionRepositoryInterface correctionRepository;
	@Autowired
	private StudentWritingAnswerRepository answerRepository;
	@Override
	public void saveWritingAnswerCorrection(int answerId, String correctionText) {
		StudentWritingAnswer answer = answerRepository.findById(answerId).orElse(null);
		if (answer == null) return;
		WritingAnswerCorrection existingCorrection = correctionRepository.findByStudentWritingAnswerStudentWritingAnswerId(answerId);
		if (existingCorrection != null) {
			existingCorrection.setCorrection(correctionText);
			existingCorrection.setStudentWritingAnswer(answer);
			existingCorrection.setCreatedAt(LocalDateTime.now());
			answer.setStudentAnswerStatus(StudentAnswerStatus.MARKED);
			answer.setWritingAnswerCorrection(existingCorrection);
			
			correctionRepository.save(existingCorrection);
			answerRepository.save(answer);
		} else {
			WritingAnswerCorrection newCorrection = new WritingAnswerCorrection();
			newCorrection.setCorrection(correctionText);
			newCorrection.setCreatedAt(LocalDateTime.now());
			newCorrection.setStudentWritingAnswer(answer);
			
			answer.setStudentAnswerStatus(StudentAnswerStatus.MARKED);
			answer.setWritingAnswerCorrection(newCorrection);
			
			correctionRepository.save(newCorrection);
			answerRepository.save(answer);
		}
	}

}
