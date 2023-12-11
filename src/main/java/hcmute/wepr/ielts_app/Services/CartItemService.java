package hcmute.wepr.ielts_app.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.CartItem;
import hcmute.wepr.ielts_app.Models.CartItemId;
import hcmute.wepr.ielts_app.Services.Interfaces.CartItemServiceInterface;
import hcmute.wepr.ielts_app.repositories.CartItemRepositoryInterface;

@Service
public class CartItemService implements CartItemServiceInterface {
	@Autowired
	private CartItemRepositoryInterface cartItemRepository;

	@Override
	public void addToCart(int userId, int courseId) {
		CartItem item = CartItem.builder()
				.cartItemId(new CartItemId().setCourseId(courseId).setUserId(userId))
				.createdAt(LocalDateTime.now())
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
}
