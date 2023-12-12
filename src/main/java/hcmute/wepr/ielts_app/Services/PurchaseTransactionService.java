package hcmute.wepr.ielts_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.PurchaseTransaction;
import hcmute.wepr.ielts_app.Services.Interfaces.PurchaseTransactionServiceInteface;
import hcmute.wepr.ielts_app.repositories.PurchaseTransactionRepositoryInterface;

@Service
public class PurchaseTransactionService implements PurchaseTransactionServiceInteface {
	@Autowired
	private PurchaseTransactionRepositoryInterface purchaseTransactionRepository;
	@Override
	public List<PurchaseTransaction> getFullDetailsPurchaseTransactions(int userId) {
		List<PurchaseTransaction> purchaseTransaction = purchaseTransactionRepository.findWithTransactionDetailsAndCourseByUserUserId(userId);
		return purchaseTransaction;
	}
	@Override
	public PurchaseTransaction getLastestPurchaseTransaction(int userId) {
		return purchaseTransactionRepository.findFirstByUserUserIdOrderByCreatedAtDesc(userId);
	}

}
