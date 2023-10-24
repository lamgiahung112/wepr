package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Utilities.Requests.SignUpUserRequest;

public interface StudentServiceInterface extends UserServiceInterface {
	void createNewStudent(SignUpUserRequest request);
}
