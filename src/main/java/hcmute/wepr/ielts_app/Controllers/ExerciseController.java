package hcmute.wepr.ielts_app.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.StudentWritingAnswer;
import hcmute.wepr.ielts_app.Models.WritingAnswerCorrection;
import hcmute.wepr.ielts_app.Models.WritingExercise;
import hcmute.wepr.ielts_app.Models.enums.StudentAnswerStatus;
import hcmute.wepr.ielts_app.Services.Interfaces.ExerciseServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.StudentWritingAnswerServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.responses.AnswerAttemptResponse;
import hcmute.wepr.ielts_app.Utilities.responses.WritingCorrectionResponse;
import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@Controller
@CrossOrigin
@RequestMapping("/exercise")
public class ExerciseController {
	
	@Autowired
	private ExerciseServiceInterface exerciseService;
	@Autowired
	private StudentWritingAnswerServiceInterface studentWritingAnswerService;
	@Autowired
	private UserServiceInterface userService;
	
	@GetMapping("/{lessonId}")
	@IsStudent
	public String exercisePage(Authentication auth, Model model, @PathVariable("lessonId") int lessonId) {
		WritingExercise exercise = exerciseService.getExerciseOfLesson(lessonId);
		int studentId = Integer.valueOf(auth.getCredentials().toString());
		List<StudentWritingAnswer> answerAttemps = studentWritingAnswerService.getAllAnswerAttemptOfUserInAnExercise(studentId, lessonId);
		model.addAttribute("exercise", exercise);
		model.addAttribute("answerAttemps", answerAttemps);
		return "student/exercise";
	}
	
	@GetMapping("/answerAttempts/{lessonId}")
	@IsStudent
	@ResponseBody
	public ResponseEntity<List<AnswerAttemptResponse>> getAnswerAttempsOfLessonExercise(Authentication auth, Model model, @PathVariable("lessonId") int lessonId) {
		int studentId = Integer.valueOf(auth.getCredentials().toString());
		WritingExercise exercise = exerciseService.getExerciseOfLesson(lessonId);
		List<StudentWritingAnswer> answerAttempts = studentWritingAnswerService.getAllAnswerAttemptWithCorrectionOfUserInAnExercise(studentId, exercise.getExerciseId());
	    List<AnswerAttemptResponse> responseList = mapToResponseList(answerAttempts);
		return ResponseEntity.ok(responseList);
	}
	
	private List<AnswerAttemptResponse> mapToResponseList(List<StudentWritingAnswer> answerAttempts) {
	    List<AnswerAttemptResponse> responseList = new ArrayList<AnswerAttemptResponse>();
	    
	    for (StudentWritingAnswer attempt : answerAttempts) {
	        AnswerAttemptResponse response = new AnswerAttemptResponse();
	        response.setExerciseId(attempt.getWritingExercise().getExerciseId());
	        response.setUserId(attempt.getUser().getUserId());
	        response.setAnswerId(attempt.getStudentWritingAnswerId());
	        response.setAnswer(attempt.getAnswer());
	        response.setCreateAt(attempt.getCreatedAt());
	        response.setStudentAnswerStatus(attempt.getStudentAnswerStatus());
	        
	        if (attempt.getWritingAnswerCorrection() != null) {
	            WritingAnswerCorrection correction = attempt.getWritingAnswerCorrection();
	            WritingCorrectionResponse correctionResponse = new WritingCorrectionResponse();
	            correctionResponse.setId(correction.getWritingAnswerCorrectionId());
	            correctionResponse.setAnswerId(correction.getStudentWritingAnswer().getStudentWritingAnswerId());
	            correctionResponse.setCorrection(correction.getCorrection());
	            correctionResponse.setCreatedAt(correction.getCreatedAt());
	            
	            response.setWritingCorrectionResponse(correctionResponse);
	        } 
	        responseList.add(response);
	    }
	    return responseList;
	}
	
	@PostMapping("/submit/{lessonId}")
	@IsStudent
	@ResponseBody
	public ResponseEntity<?> submitExercise(Authentication auth, @PathVariable("lessonId") int lessonId, @RequestParam("answer") String answer) {
		StudentWritingAnswer answerAttempt = new StudentWritingAnswer();
		int studentId = Integer.valueOf(auth.getCredentials().toString());
		ApplicationUser user = userService.findById(studentId);
		WritingExercise writingExercise = exerciseService.getExerciseOfLesson(lessonId);
		
		answerAttempt.setUser(user);
		answerAttempt.setCreatedAt(LocalDateTime.now());
		answerAttempt.setWritingExercise(writingExercise);
		answerAttempt.setStudentAnswerStatus(StudentAnswerStatus.PENDING);
		answerAttempt.setAnswer(answer);
		
		studentWritingAnswerService.createAnswerAttempt(answerAttempt);
		return ResponseEntity.ok("submit exercise successfully");
	}
}
