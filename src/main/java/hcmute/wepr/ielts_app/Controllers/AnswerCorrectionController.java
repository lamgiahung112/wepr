package hcmute.wepr.ielts_app.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Services.Interfaces.WritingAnswerCorrectionServiceInterface;
import hcmute.wepr.ielts_app.security.annotations.IsTeacher;

@Controller
@CrossOrigin
@RequestMapping("/correction")
public class AnswerCorrectionController {
	
	@Autowired
	WritingAnswerCorrectionServiceInterface correctionService;
	
	@PostMapping("/{answerId}")
	@IsTeacher
	@ResponseBody
	public ResponseEntity<?> saveAnswerCorrection(@PathVariable("answerId") int answerId, @RequestParam("correctionText") String correctionText) {
		correctionService.saveWritingAnswerCorrection(answerId, correctionText);
		return ResponseEntity.ok("save answer correction success");
	}
}
