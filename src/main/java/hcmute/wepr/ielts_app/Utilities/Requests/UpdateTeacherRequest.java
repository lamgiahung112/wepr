package hcmute.wepr.ielts_app.Utilities.Requests;

import lombok.Data;

@Data
public class UpdateTeacherRequest {
	private String email;
	private String address;
	private String phoneNumber;
	private String cvLink;
	private String experienceDescription;
}
