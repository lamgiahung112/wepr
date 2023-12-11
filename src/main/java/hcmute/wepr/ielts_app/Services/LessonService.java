package hcmute.wepr.ielts_app.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Services.Interfaces.LessonServiceInterface;
import hcmute.wepr.ielts_app.repositories.LessonRepositoryInterface;

@Service
public class LessonService implements LessonServiceInterface{
	
	@Autowired
	private LessonRepositoryInterface lessonRepository; 
	
	@Override
	public Optional<Lesson> getLessonById(int lessonId) {
		return lessonRepository.findById(lessonId);
	}

}
