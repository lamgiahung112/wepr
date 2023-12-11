package hcmute.wepr.ielts_app.Utilities.responses;

import hcmute.wepr.ielts_app.Models.Lesson;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LessonResponse {
	private int lessonId;
	private String video;
	private String title;
	private String description;
}
