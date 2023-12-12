package hcmute.wepr.ielts_app.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hcmute.wepr.ielts_app.Models.CartItem;
import hcmute.wepr.ielts_app.Models.Review;
import hcmute.wepr.ielts_app.Services.Interfaces.CartItemServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.CartItemRequest;
import hcmute.wepr.ielts_app.Utilities.responses.CartItemResponse;
import hcmute.wepr.ielts_app.Utilities.responses.CartResponse;
import hcmute.wepr.ielts_app.repositories.ReviewRepositoryInterface;
import hcmute.wepr.ielts_app.security.annotations.IsAuthenticated;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepositoryInterface reviewRepository;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

}
