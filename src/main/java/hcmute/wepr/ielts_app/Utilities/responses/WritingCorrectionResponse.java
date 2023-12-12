package hcmute.wepr.ielts_app.Utilities.responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WritingCorrectionResponse {
	private int id;
	private int answerId;
	private String correction;
	private LocalDateTime createdAt;
}
