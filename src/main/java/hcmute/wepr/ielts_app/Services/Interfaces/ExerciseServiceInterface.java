package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Models.WritingExercise;

public interface ExerciseServiceInterface {
	void createExercise();
	WritingExercise getExerciseOfLesson(int lessonId);
}
