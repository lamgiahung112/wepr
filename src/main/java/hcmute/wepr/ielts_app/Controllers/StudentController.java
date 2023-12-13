package hcmute.wepr.ielts_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateStudentRequest;
import hcmute.wepr.ielts_app.repositories.UserRepositoryInterface;
import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@Controller
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
	@Autowired
	private UserRepositoryInterface userRepository;
	@Autowired
	private UserServiceInterface userService;
	
	@RequestMapping("/profile")
	@IsStudent
	public String getProfilePage(Authentication authentication, Model model) {
		if (authentication != null && authentication.isAuthenticated()) {
	        // Lấy thông tin người dùng từ đối tượng Authentication
	        String username = authentication.getName();
	        String userid = (String) authentication.getCredentials();
	        ApplicationUser userInfo = userRepository.findUserWithProfileByUserId(Integer.parseInt(userid));
	        // Gửi thông tin người dùng tới View
	        model.addAttribute("user", userInfo);
	    }
		
		return "student/profile";
	}
	
	@RequestMapping("/profile/update")
	@IsStudent
	@ResponseBody
	public ResponseEntity<Void> updateProfile(Authentication auth, @RequestBody UpdateStudentRequest request) {
		int userId = Integer.valueOf(auth.getCredentials().toString());
		userService.updateStudent(userId, request);
		return ResponseEntity.ok().build();
	}
}