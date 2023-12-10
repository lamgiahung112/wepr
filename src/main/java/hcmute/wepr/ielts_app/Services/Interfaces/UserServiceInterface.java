package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.enums.Role;
import hcmute.wepr.ielts_app.Utilities.responses.TeacherNameDTO;

public interface UserServiceInterface {
	String authenticateAndGetCredentials(String username, String password);
	ApplicationUser createUser(String username, String password, Role role, String email, float balance);
	List<TeacherNameDTO> getTeacherNameAndUsername();
}
