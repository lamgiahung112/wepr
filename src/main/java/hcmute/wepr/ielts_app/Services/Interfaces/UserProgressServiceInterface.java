package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Models.UserProgress;

public interface UserProgressServiceInterface {
	UserProgress findByUserProgressId(int userId, int courseId);

	void updateUserProgress(int userId, int courseId, int lessonId);
}
