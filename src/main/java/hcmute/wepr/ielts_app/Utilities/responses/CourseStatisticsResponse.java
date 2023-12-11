package hcmute.wepr.ielts_app.Utilities.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseStatisticsResponse {
	private long numberOfStudents;
	private double estimatedRevenue;
	private long ratingCount;
	private double averageRating;
}
