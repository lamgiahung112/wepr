package hcmute.wepr.ielts_app.Utilities.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilteredCourseResponse {
	List<CourseDTO> courses;
	Long totalNumber;
}
