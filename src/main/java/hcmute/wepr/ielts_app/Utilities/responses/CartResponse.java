package hcmute.wepr.ielts_app.Utilities.responses;

import java.util.List;

import hcmute.wepr.ielts_app.Models.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartResponse {
	private int userId;
	private String username;
	private UserProfile userProfile;
	private List<CartItemResponse> cartItems;
}
