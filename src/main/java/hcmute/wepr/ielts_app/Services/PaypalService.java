package hcmute.wepr.ielts_app.Services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.CartItem;
import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Models.PurchaseTransaction;
import hcmute.wepr.ielts_app.Models.TransactionDetail;
import hcmute.wepr.ielts_app.Models.TransactionDetailId;
import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Models.UserProgressId;
import hcmute.wepr.ielts_app.Services.Interfaces.PaypalServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.PaymentRequest;
import hcmute.wepr.ielts_app.repositories.CartItemRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.CourseRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.PurchaseTransactionRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.TransactionDetailsRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserProgressRepositoryInterface;
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
	@Autowired
	private UserProgressRepositoryInterface userProgressRepository;

	@Override
	public void createPayment(PaymentRequest request) {
		ApplicationUser user = userRepository.findById(request.getUserId()).orElse(null);

		PurchaseTransaction transaction = PurchaseTransaction.builder().amount(request.getAmount())
				.createdAt(LocalDateTime.now()).paypalTransactionId(request.getPaypalOrderId()).user(user).build();
		PurchaseTransaction savedTransaction = purchaseTransactionRepository.save(transaction);

		List<CartItem> userCart = cartItemRepository.findWithCourseWithUserByCartItemIdUserId(request.getUserId());
		userCart.stream().forEach(cartItem -> {
			ApplicationUser author = cartItem.getCourse().getUser();
			author.setBalance(author.getBalance() + cartItem.getCourse().getPrice() * AUTHOR_REVENUE_PERCENTAGE);

			// Allow user to learn these courses
			UserProgress userProgress = new UserProgress();
			userProgress.setUserProgressId(new UserProgressId(request.getUserId(), cartItem.getCourse().getCourseId()));
			System.out.println(request.getUserId() + ";" + cartItem.getCourse().getCourseId());
			Course acceptedCourse = courseRepositoryInterface.findCourseWithLessonsByCourseId(cartItem.getCourse().getCourseId());
			Set<Lesson> courseLessons = acceptedCourse.getLessons();
			// Sort the lessons by increasing lessonId
			List<Lesson> sortedLessonList = courseLessons.stream()
			        .sorted(Comparator.comparingInt(Lesson::getLessonId)) // Sort by lessonId
			        .collect(Collectors.toList()); // Collect to List
			userProgress.setLesson(sortedLessonList.get(0));
			userProgress.setCourse(acceptedCourse);
			userProgress.setUser(user);
			userProgressRepository.save(userProgress);
			

			TransactionDetail transDetail = new TransactionDetail();
			transDetail.setTransactionDetailId(new TransactionDetailId(savedTransaction.getPaypalTransactionId(),
					cartItem.getCourse().getCourseId()));
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
