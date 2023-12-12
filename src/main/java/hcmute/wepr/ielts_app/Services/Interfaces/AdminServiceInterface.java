package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Utilities.Requests.AdminGetUserListRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.AdminStatisticsRequest;
import hcmute.wepr.ielts_app.Utilities.responses.AdminStatisticsResponse;

public interface AdminServiceInterface {
	AdminStatisticsResponse getStatistics(AdminStatisticsRequest request);
	List<ApplicationUser> getUserList(AdminGetUserListRequest request);
	void disableUser(int userId);
	void enableUser(int userId);
}
