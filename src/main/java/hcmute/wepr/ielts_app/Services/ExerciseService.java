package hcmute.wepr.ielts_app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.WritingExercise;
import hcmute.wepr.ielts_app.Services.Interfaces.ExerciseServiceInterface;
import hcmute.wepr.ielts_app.repositories.ExerciseRepositoryInterface;

@Service
public class ExerciseService implements ExerciseServiceInterface {

	@Autowired
	private ExerciseRepositoryInterface exerciseRepository;
	@Override
	public void createExercise() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WritingExercise getExerciseOfLesson(int lessonId) {
		return exerciseRepository.findByLessonLessonId(lessonId);
	}

}
