package hcmute.wepr.ielts_app.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.JwtDataWrapper;
import hcmute.wepr.ielts_app.Utilities.JwtUtils;
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
	public String authenticateAndGetCredentials(String username, String password) {
		Optional<ApplicationUser> user = userRepository.findByUsername(username);
		
		if (user.isEmpty()) {
			return null;
		}
		
		boolean isPasswordMatched = passwordEncoder.matches(password, user.get().getHashPassword());
		if (!isPasswordMatched) {
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

}
