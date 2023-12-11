package hcmute.wepr.ielts_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.CartItem;
import hcmute.wepr.ielts_app.Models.CartItemId;

public interface CartItemRepositoryInterface extends JpaRepository<CartItem, CartItemId>{
	List<CartItem> findByCartItemIdUserId(int userId);
}
