package hcmute.wepr.ielts_app.Services.Interfaces;


public interface UserServiceInterface {
	String authenticateAndGetCredentials(String username, String password);
	void createUser(String username, String password, String role, String email, float balance);
}
