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

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

	@Override
	public void rateCourse(RateCourseRequest request) {
		// TODO Auto-generated method stub
		
	}

    // Các phương thức khác nếu cần (lấy đánh giá theo ID, cập nhật đánh giá, xóa đánh giá, ...)
}
