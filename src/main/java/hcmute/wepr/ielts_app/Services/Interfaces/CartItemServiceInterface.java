package hcmute.wepr.ielts_app.Services.Interfaces;

public interface CartItemServiceInterface {
	void addToCart(int userId, int courseId);
	void deleteFromCart(int userId, int courseId);
	void clear(int userId);
}
