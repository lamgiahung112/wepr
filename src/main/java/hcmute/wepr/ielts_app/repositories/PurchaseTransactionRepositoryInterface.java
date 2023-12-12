package hcmute.wepr.ielts_app.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.PurchaseTransaction;

public interface PurchaseTransactionRepositoryInterface extends JpaRepository<PurchaseTransaction, String> {
	PurchaseTransaction findWithTransactionDetailsAndCourseByUserUserId(int userId);
	List<PurchaseTransaction> findWithTransactionDetailsWithCourseByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}
