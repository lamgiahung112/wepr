package hcmute.wepr.ielts_app.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Models.StudentWritingAnswer;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.StudentWritingAnswerServiceInterface;
import hcmute.wepr.ielts_app.Utilities.responses.AnswerAttemptResponse;
import hcmute.wepr.ielts_app.Utilities.responses.WritingCorrectionResponse;
import hcmute.wepr.ielts_app.Utilities.responses.WritingExerciseResponse;
import hcmute.wepr.ielts_app.security.annotations.IsTeacher;

@Controller
@CrossOrigin
@RequestMapping("/answer")
public class AnswerController {
	
	@Autowired
	private CourseServiceInterface courseService;
	@Autowired
	private StudentWritingAnswerServiceInterface studentWritingAnswerService;
	
	@GetMapping("/course/{courseId}")
	@IsTeacher
	@ResponseBody
	public ResponseEntity<List<AnswerAttemptResponse>> getAllStudentAnswersInACourse(@PathVariable("courseId") int courseId) {
	    Course course = courseService.getCourseWithAllLessonsAndAssociatedExercise(courseId);
	    List<Integer> exerciseIds = new ArrayList<>();

	    for (Lesson lesson : course.getLessons()) {
	        if (lesson.getWritingExercise() != null) {
	            int exerciseId = lesson.getWritingExercise().getExerciseId();
	            exerciseIds.add(exerciseId);
	        }
	    }
	    
	    List<AnswerAttemptResponse> allStudentAnswers = new ArrayList<>();
	    
	    // Get all student answers for each exercise in the course
	    for (int exerciseId : exerciseIds) {
	        List<StudentWritingAnswer> studentAnswers = studentWritingAnswerService.getAllAnswerAttemptOfAnExercise(exerciseId);
	        for (StudentWritingAnswer answer : studentAnswers) {
	            AnswerAttemptResponse attemptResponse = new AnswerAttemptResponse();
	            attemptResponse.setExerciseId(answer.getWritingExercise().getExerciseId());
	            attemptResponse.setUserId(answer.getUser().getUserId());
	            attemptResponse.setAnswerId(answer.getStudentWritingAnswerId());
	            attemptResponse.setAnswer(answer.getAnswer());
	            attemptResponse.setCreateAt(answer.getCreatedAt());
	            attemptResponse.setStudentAnswerStatus(answer.getStudentAnswerStatus());
	            
	            allStudentAnswers.add(attemptResponse);
	        }
	    }

	    return ResponseEntity.ok(allStudentAnswers);
	}
	
	@GetMapping("/{answerId}")
	@IsTeacher
	@ResponseBody
	public ResponseEntity<AnswerAttemptResponse> getStudentAnswerWithExerciseAndCorrection(@PathVariable("answerId") int answerId) {
		StudentWritingAnswer answer = studentWritingAnswerService.getAnswerWithExerciseAndCorrection(answerId);
		if (answer == null) {
	        return ResponseEntity.notFound().build();
	    }

	    // Mapping logic within the controller method
	    AnswerAttemptResponse response = new AnswerAttemptResponse();
	    response.setExerciseId(answer.getWritingExercise().getExerciseId());
	    response.setUserId(answer.getUser().getUserId());
	    response.setAnswerId(answer.getStudentWritingAnswerId());
	    response.setAnswer(answer.getAnswer());
	    response.setCreateAt(answer.getCreatedAt());
	    response.setStudentAnswerStatus(answer.getStudentAnswerStatus());
	    response.setWritingExercise(new WritingExerciseResponse(answer.getWritingExercise().getExerciseId(), answer.getWritingExercise().getTitle()));
	    if (answer.getWritingAnswerCorrection() != null) {
	    	response.setWritingCorrectionResponse(new WritingCorrectionResponse(answer.getWritingAnswerCorrection().getWritingAnswerCorrectionId(),
		    		answer.getWritingAnswerCorrection().getStudentWritingAnswer().getStudentWritingAnswerId(), answer.getWritingAnswerCorrection().getCorrection(),
		    		answer.getWritingAnswerCorrection().getCreatedAt()));
	    }
	    

	    return ResponseEntity.ok(response);
	}
}
