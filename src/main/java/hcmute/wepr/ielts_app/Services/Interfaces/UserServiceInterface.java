package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.enums.Role;

public interface UserServiceInterface {
	String authenticateAndGetCredentials(String username, String password);
	ApplicationUser createUser(String username, String password, Role role, String email, float balance);
}
