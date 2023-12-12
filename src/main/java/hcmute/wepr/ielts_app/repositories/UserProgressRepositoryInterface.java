package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Models.UserProgressId;

public interface UserProgressRepositoryInterface extends JpaRepository<UserProgress, UserProgressId> {
	UserProgress findByUserProgressId(UserProgressId userProgressId);
}
