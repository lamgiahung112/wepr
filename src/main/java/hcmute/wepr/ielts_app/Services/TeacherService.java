package hcmute.wepr.ielts_app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.UserProfile;
import hcmute.wepr.ielts_app.Services.Interfaces.TeacherServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpTeacherRequest;
import hcmute.wepr.ielts_app.repositories.UserProfileRepositoryInterface;

@Service
public class TeacherService implements TeacherServiceInterface {
	@Autowired
	private UserProfileRepositoryInterface userProfileRepository;
	
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
	public UserProfile updateTeacherProfile(Integer id, UserProfile updateTeacher) {
        UserProfile userProfile = userProfileRepository.findById(id).orElse(null);

        if (userProfile != null) {
            userProfile.setName(updateTeacher.getName());
            userProfile.setAddress(updateTeacher.getAddress());
            userProfile.setCvLink(updateTeacher.getCvLink());
            userProfile.setExperienceDescription(updateTeacher.getExperienceDescription());
            userProfile.setPhoneNumber(updateTeacher.getPhoneNumber());

            return userProfileRepository.save(userProfile);
        }

        return null; // Handle if user is not found or other error cases
    }
}
