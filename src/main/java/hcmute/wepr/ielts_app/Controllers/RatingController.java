package hcmute.wepr.ielts_app.Controllers;

import hcmute.wepr.ielts_app.Models.Rating;
import hcmute.wepr.ielts_app.Services.RatingService;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @PostMapping("/rate-course")
    public ResponseEntity<Void> rateCourse(@RequestBody RateCourseRequest request) {
        ratingService.rateCourse(request);
        return ResponseEntity.ok().build();
    }


}
