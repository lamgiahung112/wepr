package hcmute.wepr.ielts_app.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.enums.Role;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.JwtDataWrapper;
import hcmute.wepr.ielts_app.Utilities.JwtUtils;
import hcmute.wepr.ielts_app.Utilities.responses.TeacherNameDTO;
import hcmute.wepr.ielts_app.repositories.UserRepositoryInterface;

@Service
public class UserService implements UserServiceInterface {
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserRepositoryInterface userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public String authenticateAndGetCredentials(String username, String password, Role role) {
		Optional<ApplicationUser> user = userRepository.findByUsername(username);
		
		if (user.isEmpty() || user.get().getRole() != role) {
			return null;
		}
		
		boolean isPasswordMatched = passwordEncoder.matches(password, user.get().getHashPassword());
		if (!isPasswordMatched || !user.get().isEnabled()) {
			return null;
		}
		
		JwtDataWrapper data = JwtDataWrapper.builder()
				.id(String.valueOf(user.get().getUserId()))
				.email(user.get().getEmail())
				.username(user.get().getUsername())
				.role(user.get().getRole().name())
				.build();
		return jwtUtils.generateToken(data);
	}

	@Override
	public ApplicationUser createUser(String username, String password, Role role, String email, float balance) {
		Optional<ApplicationUser> existingUser = userRepository.findByUsername(username);
		if (existingUser.isEmpty()) {
			ApplicationUser user = new ApplicationUser();
			user.setUsername(username);
			user.setHashPassword(passwordEncoder.encode(password));
			user.setRole(role);
			user.setEmail(email);
			user.setBalance(balance);
			user.setEnabled(true);
			
			return userRepository.save(user);
		} else {
			// response error
			return null;
		}
	}

	@Override
	public List<TeacherNameDTO> getTeacherNameAndUsername() {
		List<ApplicationUser> teachers = userRepository.findWithUserProfileByRole(Role.ROLE_TEACHER);
		List<TeacherNameDTO> teacherNameDTOs = new ArrayList<>();

	    for (ApplicationUser teacher : teachers) {
	        TeacherNameDTO dto = new TeacherNameDTO();
	        dto.setUsername(teacher.getUsername());
	        if (teacher.getProfile() != null) {
		        dto.setName(teacher.getProfile().getName());
	        } else {
	        	dto.setName(teacher.getUsername());
	        }
	        System.out.println(teacher.getProfile());
	        teacherNameDTOs.add(dto);
	    }
	    return teacherNameDTOs;
	}

	@Override
	public ApplicationUser findById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public ApplicationUser findWithUserProfileById(int id) {
		return userRepository.findUserWithProfileByUserId(id);
	}

	

}
