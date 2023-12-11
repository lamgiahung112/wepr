package hcmute.wepr.ielts_app.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Services.Interfaces.LessonServiceInterface;
import hcmute.wepr.ielts_app.Utilities.responses.LessonResponse;

@Controller
@CrossOrigin
@RequestMapping("/lessons")
public class LessonController {
	
	@Autowired
	private LessonServiceInterface lessonService;
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<LessonResponse> getLessonById(@PathVariable("id") int lessonId) {
		Optional<Lesson> lesson = lessonService.getLessonById(lessonId);
		if (lesson.isPresent()) {
			Lesson l = lesson.get();
			return ResponseEntity.status(HttpStatus.OK).body(new LessonResponse(l.getLessonId(), l.getVideo(),l.getTitle(), l.getDescription()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
