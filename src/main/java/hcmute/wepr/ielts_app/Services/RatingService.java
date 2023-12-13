package hcmute.wepr.ielts_app.Services;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.Rating;
import hcmute.wepr.ielts_app.Models.RatingId;
import hcmute.wepr.ielts_app.Services.Interfaces.RatingServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.repositories.CourseRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.RatingRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserRepositoryInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RatingService implements RatingServiceInterface {

    @Autowired
    private RatingRepositoryInterface ratingRepository;

	@Autowired
	private CourseRepositoryInterface courseRepository;
	
	@Autowired
	private UserRepositoryInterface userRepository;

    @Override
    public List<Rating> getAllRatingsOfACourse(int courseId) {
        return ratingRepository.findByRatingIdCourseId(courseId);
    }

	@Override
	public void rateCourse(RateCourseRequest request) {
		Course course = courseRepository.findById(request.getCourseId()).orElse(null);
		ApplicationUser user = userRepository.findById(request.getUserId()).orElse(null);
		Rating rating = Rating.builder()
				.ratingId(new RatingId().setCourseId(request.getCourseId()).setUserId(request.getUserId()))
				.rating(request.getRating()).comment(request.getComment())
				.course(course)
				.user(user)
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
		
		course.setRating(totalRatingPoint.get()/totalRatingCount.get());
		courseRepository.save(course);
	}

}
