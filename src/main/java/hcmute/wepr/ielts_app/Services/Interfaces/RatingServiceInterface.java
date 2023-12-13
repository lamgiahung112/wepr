package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Models.Rating;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;

import java.util.List;

public interface RatingServiceInterface {
    List<Rating> getAllRatingsOfACourse(int courseId);
    void rateCourse(RateCourseRequest request);


}
