package hcmute.wepr.ielts_app.Services;

import hcmute.wepr.ielts_app.Models.Rating;
import hcmute.wepr.ielts_app.Services.Interfaces.RatingServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.repositories.RatingRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements RatingServiceInterface {

    @Autowired
    private RatingRepositoryInterface ratingRepository;

	@Autowired
	private CourseRepositoryInterface courseRepository;

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

	@Override
	public void rateCourse(RateCourseRequest request) {
		Rating rating = Rating.builder()
				.ratingId(new RatingId().setCourseId(request.getCourseId()).setUserId(request.getUserId()))
				.rating(request.getRating()).comment(request.getComment())
				.build();
		ratingRepository.save(rating);
		AtomicInteger totalRatingPoint = new AtomicInteger(0);
		AtomicInteger totalRatingCount = new AtomicInteger(0);
		ratingRepository.findByRatingIdCourseId(request.getCourseId())
			.stream()
			.forEach(r -> {
				totalRatingCount.getAndIncrement();
				totalRatingPoint.getAndAdd(r.getRating());
			});
		Course course = courseRepository.findById(request.getCourseId()).orElse(null);
		course.setRating(totalRatingPoint.get()/totalRatingCount.get());
		courseRepository.save(course);
	}

    // Các phương thức khác nếu cần (lấy đánh giá theo ID, cập nhật đánh giá, xóa đánh giá, ...)
}
