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
public class CourseDTO {
	private int courseId;
	private String courseName;
	private String coverImage;
	private LocalDateTime createdAt;
	private float price;
	private float rating;
	private int enrolledNumber;
	private String author; // Add author field
	private String difficulty; // Add difficulty field
}
