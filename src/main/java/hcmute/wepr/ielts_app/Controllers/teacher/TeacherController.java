package hcmute.wepr.ielts_app.Controllers.teacher;

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

import hcmute.wepr.ielts_app.Models.UserProfile;
import hcmute.wepr.ielts_app.Services.TeacherService;
import hcmute.wepr.ielts_app.security.annotations.IsTeacher;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class TeacherController {
	
	@RequestMapping("/dashboard")
	@IsTeacher
	public String getDashboardPage() {
		return "teacher/dashboard";
	}
	
	@RequestMapping("/profile")
	@IsTeacher
	public String getProfile() {
		return "teacher/profile_teacher";
	}
	
	private final TeacherService teacherService; 

	//@Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
	
    @GetMapping("/profile/{id}")
    @IsTeacher
    public String getTeacherByID(@PathVariable Integer id, Model model) {
        UserProfile userProfile = teacherService.getTeacherByID(id);
        model.addAttribute("teacherProfile", userProfile);
        return "teacher/profile_teacher"; // Trả về tên của file HTML (template) Thymeleaf
    }

    @PostMapping("/profile/{id}/update")
    @IsTeacher
    public String updateTeacherProfile(@PathVariable Integer id, @ModelAttribute("teacherProfile") UserProfile updatedProfile) {
        teacherService.updateTeacherProfile(id, updatedProfile);
        return "redirect:/teacher/profile_teacher/" + id;
    }
}
