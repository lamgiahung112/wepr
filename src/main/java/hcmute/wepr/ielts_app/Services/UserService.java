package hcmute.wepr.ielts_app.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository _userRepository;
	public Optional<ApplicationUser> loadByUsername(String username) {
		System.out.println("In the user service");
		return _userRepository.findApplicationUserByUsername(username);
	}
}
