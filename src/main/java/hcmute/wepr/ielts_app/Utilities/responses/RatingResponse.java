package hcmute.wepr.ielts_app.Utilities.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingResponse {
	private int userId;
	private int courseId;
	private int rating;
	private String comment;
}
