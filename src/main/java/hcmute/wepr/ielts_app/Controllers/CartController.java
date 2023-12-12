package hcmute.wepr.ielts_app.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.CartItem;
import hcmute.wepr.ielts_app.Services.Interfaces.CartItemServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.CartItemRequest;
import hcmute.wepr.ielts_app.Utilities.responses.CartItemResponse;
import hcmute.wepr.ielts_app.Utilities.responses.CartResponse;
import hcmute.wepr.ielts_app.security.annotations.IsAuthenticated;

@RequestMapping("/cart")
@Controller
@CrossOrigin
public class CartController {
	@Value("${paypal.client.id}")
	private String paypalClientId;
	
	@Autowired
	private CartItemServiceInterface cartItemService;
	
	@PostMapping("/add")
	@ResponseBody
	@IsAuthenticated
	public ResponseEntity<?> addToCart(Authentication authentication, @RequestBody CartItemRequest request) {
		cartItemService.addToCart(Integer.valueOf(authentication.getCredentials().toString()), request.getCourseId());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/remove")
	@ResponseBody
	@IsAuthenticated
	public ResponseEntity<?> removeFromCart(Authentication authentication, @RequestBody CartItemRequest request) {
		cartItemService.deleteFromCart(Integer.valueOf(authentication.getCredentials().toString()), request.getCourseId());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/clear")
	@ResponseBody
	@IsAuthenticated
	public ResponseEntity<?> clearCart(Authentication authentication) {
		cartItemService.clear(Integer.valueOf(authentication.getCredentials().toString()));
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/details")
	@IsAuthenticated
	public String getCartDetailsPage(Model model, Authentication authentication) {
		float totalPriceCart = cartItemService.getTotalPriceOfCart(Integer.valueOf(authentication.getCredentials().toString()));
		
		model.addAttribute("total", totalPriceCart);
		model.addAttribute("clientId", paypalClientId);
		model.addAttribute("userId", Integer.valueOf(authentication.getCredentials().toString()));
		return "student/cart_payment";
	}
	
	public List<CartItemResponse> convertToCartItemResponseList(List<CartItem> cartItems) {
        List<CartItemResponse> cartItemResponses = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            CartItemResponse cartItemResponse = new CartItemResponse();
            
            cartItemResponse.setUserId(cartItem.getUser().getUserId()); // Assuming getId() retrieves the user ID
            cartItemResponse.setCourseId(cartItem.getCourse().getCourseId()); // Assuming getId() retrieves the course ID
            cartItemResponse.setCourseImage(cartItem.getCourse().getCoverImage()); // Assuming getImage() retrieves the course image
            cartItemResponse.setCourseName(cartItem.getCourse().getCourseName()); // Assuming getName() retrieves the course name
            cartItemResponse.setPrice(cartItem.getCourse().getPrice()); // Assuming getPrice() retrieves the course price

            cartItemResponses.add(cartItemResponse);
        }

        return cartItemResponses;
    }
	
	@GetMapping("/cartItems/{userId}")
	@IsAuthenticated
	@ResponseBody
	public ResponseEntity<CartResponse> getAllCartItemsOrUser(@PathVariable("userId") int userId) {
		List<CartItem> cartItems = cartItemService.getAllUserCartItems(userId);
		List<CartItemResponse> cartItemResponses = convertToCartItemResponseList(cartItems);
		CartResponse cartResponse = new CartResponse();
		cartResponse.setCartItems(cartItemResponses);
		cartResponse.setUserId(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(cartResponse);
	}
}
