package hcmute.wepr.ielts_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.Rating;
import hcmute.wepr.ielts_app.Models.RatingId;

public interface RatingRepositoryInterface extends JpaRepository<Rating, RatingId> {
	List<Rating> findByRatingIdCourseId(int courseId);
	Rating findByRatingIdCourseIdAndRatingIdUserId(int courseId, int userId);
}
