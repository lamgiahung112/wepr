package hcmute.wepr.ielts_app.Utilities.Requests;

import lombok.Data;

@Data
public class RateCourseRequest {
	private int courseId;
	private int userId;
	private int rating;
	private String comment;
}
