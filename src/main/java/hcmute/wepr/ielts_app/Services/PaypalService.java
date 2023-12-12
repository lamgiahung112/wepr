package hcmute.wepr.ielts_app.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.CartItem;
import hcmute.wepr.ielts_app.Models.PurchaseTransaction;
import hcmute.wepr.ielts_app.Models.TransactionDetail;
import hcmute.wepr.ielts_app.Models.TransactionDetailId;
import hcmute.wepr.ielts_app.Services.Interfaces.PaypalServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.PaymentRequest;
import hcmute.wepr.ielts_app.repositories.CartItemRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.CourseRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.PurchaseTransactionRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.TransactionDetailsRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserRepositoryInterface;

@Service
public class PaypalService implements PaypalServiceInterface {
	@Value("${config.share_percentage}")
	private float AUTHOR_REVENUE_PERCENTAGE;
	@Autowired
	private CartItemRepositoryInterface cartItemRepository;
	@Autowired
	private PurchaseTransactionRepositoryInterface purchaseTransactionRepository;
	@Autowired
	private UserRepositoryInterface userRepository;
	@Autowired
	private TransactionDetailsRepositoryInterface transactionDetailRepository;
	@Autowired
	private CourseRepositoryInterface courseRepositoryInterface;
	
	@Override
	public void createPayment(PaymentRequest request) {
		ApplicationUser user = userRepository.findById(request.getUserId()).orElse(null);
		
		PurchaseTransaction transaction = PurchaseTransaction.builder()
				.amount(request.getAmount())
				.createdAt(LocalDateTime.now())
				.paypalTransactionId(request.getPaypalOrderId())
				.user(user)
				.build();
		PurchaseTransaction savedTransaction = purchaseTransactionRepository.save(transaction);
		
		List<CartItem> userCart = cartItemRepository.findWithCourseWithUserByCartItemIdUserId(request.getUserId());
		userCart.stream().forEach(cartItem -> {
			ApplicationUser author = cartItem.getCourse().getUser();
			author.setBalance(author.getBalance() + cartItem.getCourse().getPrice() * AUTHOR_REVENUE_PERCENTAGE);
			TransactionDetail transDetail = new TransactionDetail();
			transDetail.setTransactionDetailId(
					new TransactionDetailId(
							savedTransaction.getPaypalTransactionId(), 
							cartItem.getCourse().getCourseId()
				)
			);
			transDetail.setCourse(cartItem.getCourse());
			transDetail.setPurchaseTransaction(savedTransaction);
			cartItem.getCourse().setEnrolledNumber(cartItem.getCourse().getEnrolledNumber() + 1);
			
			courseRepositoryInterface.save(cartItem.getCourse());
			transactionDetailRepository.save(transDetail);
			userRepository.save(author);
		});
		
		cartItemRepository.deleteAllInBatch(userCart);
		
	}
}
