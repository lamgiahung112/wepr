package hcmute.wepr.ielts_app.Utilities.Requests;

import lombok.Data;

@Data
public class AdminGetUserListRequest {
	public enum ROLE_OPTIONS {
		ALL, STUDENT, TEACHER
	}
	
	public enum ENABLE_OPTIONS {
		ALL, ENABLED, DISABLED
	}
	
	private ROLE_OPTIONS roleOption;
	private ENABLE_OPTIONS enableOption;
	private int page;
}
