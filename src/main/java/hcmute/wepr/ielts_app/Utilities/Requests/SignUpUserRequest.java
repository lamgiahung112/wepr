package hcmute.wepr.ielts_app.Utilities.Requests;

import lombok.Data;

@Data
public class SignUpUserRequest {
	private String username;
	private String email;
	private String name;
	private String password;
}
