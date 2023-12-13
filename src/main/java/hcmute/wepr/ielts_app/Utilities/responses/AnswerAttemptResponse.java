package hcmute.wepr.ielts_app.Utilities.responses;

import java.time.LocalDateTime;

import hcmute.wepr.ielts_app.Models.enums.StudentAnswerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerAttemptResponse {
	private int exerciseId;
	private int userId;
	private int answerId;
	private String answer;
	private LocalDateTime createAt;
	private StudentAnswerStatus studentAnswerStatus;
	private WritingExerciseResponse writingExercise;
	private WritingCorrectionResponse writingCorrectionResponse;
}
