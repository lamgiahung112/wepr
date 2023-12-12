package hcmute.wepr.ielts_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.Review;

public interface ReviewRepositoryInterface extends JpaRepository<Review, Long> {
    // Các phương thức tùy chọn nếu cần
}
