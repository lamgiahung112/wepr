package hcmute.wepr.ielts_app.Controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.UserProfile;
import hcmute.wepr.ielts_app.Services.Interfaces.TeacherServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateTeacherRequest;
import hcmute.wepr.ielts_app.security.annotations.IsTeacher;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class TeacherController {
	@Autowired
	private TeacherServiceInterface teacherService; 
	@Autowired
	private UserServiceInterface userService;
	
	@RequestMapping("/dashboard")
	@IsTeacher
	public String getDashboardPage() {
		return "teacher/dashboard";
	}
	
    @GetMapping("/profile")
    @IsTeacher
    public String getTeacherByID(Authentication auth, Model model) {
    	int userId = Integer.valueOf(auth.getCredentials().toString());
        ApplicationUser user = userService.findWithUserProfileById(userId);
        model.addAttribute("user", user);
        return "teacher/profile_teacher"; 
    }

    @PostMapping("/profile/update")
    @IsTeacher
    @ResponseBody
    public ResponseEntity<Void> updateTeacherProfile(Authentication auth, @RequestBody UpdateTeacherRequest request) {
    	int userId = Integer.valueOf(auth.getCredentials().toString());
    	teacherService.updateTeacherProfile(userId, request);
        return ResponseEntity.ok().build();
    }
}
