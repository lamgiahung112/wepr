package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import jakarta.mail.MessagingException;

public interface ForgetPasswordServiceInterface {
	void sendResetPasswordEmail(String username) throws MessagingException;
	boolean isValidRequest(String requestId);
	ApplicationUser getUserOfRequest(String requestId);
	void changePassword(String requestId, String password);
}
