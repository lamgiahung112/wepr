package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.UserProfile;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpTeacherRequest;

public interface TeacherServiceInterface {
	UserProfile createTeacherProfile(ApplicationUser user, SignUpTeacherRequest request);
}
