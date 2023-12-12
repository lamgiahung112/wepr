package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.Optional;

import hcmute.wepr.ielts_app.Models.Lesson;

public interface LessonServiceInterface {
	Optional<Lesson> getLessonById(int lessonId);
}
