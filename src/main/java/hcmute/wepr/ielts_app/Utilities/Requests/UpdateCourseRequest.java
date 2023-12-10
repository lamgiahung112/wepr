package hcmute.wepr.ielts_app.Utilities.Requests;

import java.util.List;

import hcmute.wepr.ielts_app.Models.enums.DifficultLevel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateCourseRequest {
	private int courseId;
	private String courseName;
	private String courseDescription;
	private String coverImageLink;
	private float price;
	private DifficultLevel difficultyLevel;
	private List<LessonRequest> lessons;
}
