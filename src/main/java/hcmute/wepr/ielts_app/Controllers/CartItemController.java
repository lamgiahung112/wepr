package hcmute.wepr.ielts_app.Controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hcmute.wepr.ielts_app.Models.CartItem;
import hcmute.wepr.ielts_app.Models.CartItemId;
import hcmute.wepr.ielts_app.Models.enums.Role;
import hcmute.wepr.ielts_app.Services.Interfaces.CartItemServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.StudentServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpUserRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    @Autowired
    private CartItemServiceInterface cartItemService;

    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemService.getAllCartItems();
    }

    @GetMapping("/{userId}/{courseId}")
    public CartItem getCartItemById(@PathVariable int userId, @PathVariable int courseId) {
        CartItemId cartItemId = new CartItemId(userId, courseId);
        return cartItemService.getCartItemById(cartItemId);
    }

    @PostMapping
    public void addCartItem(@RequestBody CartItem cartItem) {
        cartItemService.addCartItem(cartItem);
    }

    @PutMapping
    public void updateCartItem(@RequestBody CartItem cartItem) {
        cartItemService.updateCartItem(cartItem);
    }

    @DeleteMapping("/{userId}/{courseId}")
    public void deleteCartItem(@PathVariable int userId, @PathVariable int courseId) {
        CartItemId cartItemId = new CartItemId(userId, courseId);
        cartItemService.deleteCartItem(cartItemId);
    }
}

