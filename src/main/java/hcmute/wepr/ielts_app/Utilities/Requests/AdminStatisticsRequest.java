package hcmute.wepr.ielts_app.Utilities.Requests;

import lombok.Data;

@Data
public class AdminStatisticsRequest {
	public enum StatsRange {
		DAY, MONTH, YEAR
	}
	private StatsRange range;
}
