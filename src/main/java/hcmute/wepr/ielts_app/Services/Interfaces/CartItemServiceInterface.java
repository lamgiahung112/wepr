package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.CartItem;

public interface CartItemServiceInterface {
	void addToCart(int userId, int courseId);
	void deleteFromCart(int userId, int courseId);
	void clear(int userId);
	float getTotalPriceOfCart(int userId);
	List<CartItem> getAllUserCartItems(int userId);
}
