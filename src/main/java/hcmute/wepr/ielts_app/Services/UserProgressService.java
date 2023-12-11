package hcmute.wepr.ielts_app.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Models.UserProgressId;
import hcmute.wepr.ielts_app.Services.Interfaces.UserProgressServiceInterface;
import hcmute.wepr.ielts_app.repositories.LessonRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserProgressRepositoryInterface;

@Service
public class UserProgressService implements UserProgressServiceInterface {
	@Autowired
	private UserProgressRepositoryInterface userProgressRepository;
	@Autowired
	private LessonRepositoryInterface lessonRepository;
	
	
	@Override
	public UserProgress findByUserProgressId(int userId, int courseId) {
		UserProgressId userProgressId = new UserProgressId(userId, courseId);
		return userProgressRepository.findByUserProgressId(userProgressId);
	}



	@Override
	public void updateUserProgress(int userId, int courseId, int lessonId) {
		UserProgressId userProgressId = new UserProgressId(userId, courseId);
		UserProgress userProgress = userProgressRepository.findByUserProgressId(userProgressId);
		if (userProgress != null) {
			Optional<Lesson> lesson = lessonRepository.findById(lessonId);
			if (lesson.isEmpty()) return;
			userProgress.setLesson(lesson.get());
			userProgressRepository.save(userProgress);
		}
	}

}
