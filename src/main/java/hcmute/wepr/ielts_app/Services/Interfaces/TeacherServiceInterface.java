package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.UserProfile;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpTeacherRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateTeacherRequest;

public interface TeacherServiceInterface {
	UserProfile createTeacherProfile(ApplicationUser user, SignUpTeacherRequest request);
	UserProfile getTeacherByID (Integer id);
	void updateTeacherProfile(int userId, UpdateTeacherRequest request);
}
