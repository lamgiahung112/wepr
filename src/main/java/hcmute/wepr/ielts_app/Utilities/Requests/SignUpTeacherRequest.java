package hcmute.wepr.ielts_app.Utilities.Requests;

import lombok.Data;

@Data
public class SignUpTeacherRequest {
	private String username;
	private String email;
	private String name;
	private String password;
	private String address;
	private String phoneNumber;
	private String cvLink;
	private String experienceDescription;
}
