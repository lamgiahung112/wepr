package hcmute.wepr.ielts_app.Controllers;

import hcmute.wepr.ielts_app.Models.Rating;
import hcmute.wepr.ielts_app.Services.RatingService;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.Utilities.responses.RatingResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/{courseId}")
    public ResponseEntity<List<RatingResponse>> getAllRatingsOfACourse(@PathVariable("courseId") int courseId) {
        List<Rating> ratings = ratingService.getAllRatingsOfACourse(courseId);

        // Map List<Rating> to List<RatingResponse>
        List<RatingResponse> ratingResponses = ratings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ratingResponses);
    }
    
    private RatingResponse mapToResponse(Rating rating) {
        RatingResponse response = new RatingResponse();
        response.setUserId(rating.getUser().getUserId());
        response.setCourseId(rating.getCourse().getCourseId());
        response.setRating(rating.getRating());
        response.setComment(rating.getComment());
        return response;
    }

    @PostMapping("/rate-course")
    public ResponseEntity<Void> rateCourse(@RequestBody RateCourseRequest request) {
        ratingService.rateCourse(request);
        return ResponseEntity.ok().build();
    }


}
