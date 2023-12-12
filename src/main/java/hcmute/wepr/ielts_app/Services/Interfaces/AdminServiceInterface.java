package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Utilities.Requests.AdminStatisticsRequest;
import hcmute.wepr.ielts_app.Utilities.responses.AdminStatisticsResponse;

public interface AdminServiceInterface {
	AdminStatisticsResponse getStatistics(AdminStatisticsRequest request);
}
