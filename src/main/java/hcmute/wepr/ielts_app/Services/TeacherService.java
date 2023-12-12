package hcmute.wepr.ielts_app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.UserProfile;
import hcmute.wepr.ielts_app.Services.Interfaces.TeacherServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpTeacherRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateTeacherRequest;
import hcmute.wepr.ielts_app.repositories.UserProfileRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserRepositoryInterface;

@Service
public class TeacherService implements TeacherServiceInterface {
	@Autowired
	private UserProfileRepositoryInterface userProfileRepository;
	@Autowired
	private UserRepositoryInterface userRepository;
	
	@Override
	public UserProfile createTeacherProfile(ApplicationUser user, SignUpTeacherRequest request) {
		UserProfile userProfile = UserProfile.builder()
				.name(request.getName())
				.address(request.getAddress())
				.cvLink(request.getCvLink())
				.experienceDescription(request.getExperienceDescription())
				.phoneNumber(request.getPhoneNumber())
				.user(user)
				.build();
		return userProfileRepository.save(userProfile);
	}

    public TeacherService(UserProfileRepositoryInterface userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }
	
	@Override
	public UserProfile getTeacherByID (Integer id) {
		return userProfileRepository.findByUserUserId(id);
	}

	@Override
	public void updateTeacherProfile(int userId, UpdateTeacherRequest request) {
		ApplicationUser teacher = userRepository.findUserWithProfileByUserId(userId);
		
		teacher.setEmail(request.getEmail());
		teacher.getProfile().setAddress(request.getAddress());
		teacher.getProfile().setPhoneNumber(request.getPhoneNumber());
		teacher.getProfile().setCvLink(request.getCvLink());
		teacher.getProfile().setExperienceDescription(request.getExperienceDescription());

		userRepository.save(teacher);
		userProfileRepository.save(teacher.getProfile());
	}

}
