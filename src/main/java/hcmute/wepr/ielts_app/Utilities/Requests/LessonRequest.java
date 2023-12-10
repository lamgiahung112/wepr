package hcmute.wepr.ielts_app.Utilities.Requests;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class LessonRequest {
	private String title;
	private String description;
	private String videoLink;
}
