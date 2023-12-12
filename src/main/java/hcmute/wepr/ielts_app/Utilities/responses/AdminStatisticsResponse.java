package hcmute.wepr.ielts_app.Utilities.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminStatisticsResponse {
	private int numberOfSoldCourses;
	private int totalCourseValue;
	private int totalRevenue;
	private int numberOfStudents;
}
