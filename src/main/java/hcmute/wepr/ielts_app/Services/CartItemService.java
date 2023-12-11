package hcmute.wepr.ielts_app.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.CartItem;
import hcmute.wepr.ielts_app.Models.CartItemId;
import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Services.Interfaces.CartItemServiceInterface;
import hcmute.wepr.ielts_app.repositories.CartItemRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.CourseRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserRepositoryInterface;

@Service
public class CartItemService implements CartItemServiceInterface {
	@Autowired
	private CartItemRepositoryInterface cartItemRepository;
	@Autowired
	private UserRepositoryInterface userRepository;
	@Autowired
	private CourseRepositoryInterface courseRepository;

	@Override
	public void addToCart(int userId, int courseId) {
		Course couse = courseRepository.findById(courseId).orElse(null);
		ApplicationUser user = userRepository.findById(userId).orElse(null);
		CartItem item = CartItem.builder()
				.cartItemId(new CartItemId().setCourseId(courseId).setUserId(userId))
				.createdAt(LocalDateTime.now())
				.user(user)
				.course(couse)
				.build();
		
		cartItemRepository.save(item);
	}

	@Override
	public void deleteFromCart(int userId, int courseId) {
		CartItem item = cartItemRepository.findById(new CartItemId().setCourseId(courseId).setUserId(userId)).orElse(null);
		cartItemRepository.delete(item);
	}

	@Override
	public void clear(int userId) {
		List<CartItem> cart = cartItemRepository.findByCartItemIdUserId(userId);
		cartItemRepository.deleteAllInBatch(cart);
	}

	@Override
	public float getTotalPriceOfCart(int userId) {
		List<CartItem> cart = cartItemRepository.findWithCourseByCartItemIdUserId(userId);
		AtomicReference<Float> price = new AtomicReference<>(0f);
		
		cart.forEach(item -> {
			price.set(price.get() + item.getCourse().getPrice());
		});
		return price.get();
	}
}
