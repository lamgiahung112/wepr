package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.WritingAnswerCorrection;

public interface WritingAnswerCorrectionRepositoryInterface extends JpaRepository<WritingAnswerCorrection, Integer> {
	WritingAnswerCorrection findByStudentWritingAnswerStudentWritingAnswerId(int answerId);
}
