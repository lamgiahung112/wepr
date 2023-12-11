package hcmute.wepr.ielts_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Services.Interfaces.UserProgressServiceInterface;
import hcmute.wepr.ielts_app.Utilities.responses.UserProgressResponse;
import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@Controller
@CrossOrigin
@RequestMapping("/userprogress")
public class UserProgressController {
	
	@Autowired
	UserProgressServiceInterface userProgressService;
	
	@IsStudent
	@PostMapping("/updateUserProgress")
	@ResponseBody
	public ResponseEntity<?> updateUserProgress(Authentication auth, @RequestParam("userId") int userId,
            @RequestParam("courseId") int courseId,
            @RequestParam("lessonId") int lessonId) {
		int studentId = Integer.valueOf(auth.getCredentials().toString());
		if (studentId != userId) return ResponseEntity.status(HttpStatus.OK).body("You not have permission");
		userProgressService.updateUserProgress(userId, courseId, lessonId);
		return ResponseEntity.status(HttpStatus.OK).body("Update user progress successfully");
	}
	
	@IsStudent
	@GetMapping("/getUserProgress")
	@ResponseBody
	public ResponseEntity<UserProgressResponse> getUserProgress(Authentication auth, @RequestParam("userId") int userId,
            @RequestParam("courseId") int courseId) {
		int studentId = Integer.valueOf(auth.getCredentials().toString());
		if (studentId != userId) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		UserProgress userProgress = userProgressService.findByUserProgressId(userId, courseId);
		if (userProgress != null) {
			UserProgressResponse userProgressResponse = new UserProgressResponse(userProgress.getUserProgressId().getUserId(), userProgress.getUserProgressId().getCourseId(), userProgress.getLesson().getLessonId());
			return ResponseEntity.status(HttpStatus.OK).body(userProgressResponse);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
}
