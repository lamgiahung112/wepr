package hcmute.wepr.ielts_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Services.Interfaces.CartItemServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.CartItemRequest;
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
		model.addAttribute("clientId", paypalClientId);
		model.addAttribute("userId", Integer.valueOf(authentication.getCredentials().toString()));
		return "student/cart_payment";
	}
}
