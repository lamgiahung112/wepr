package hcmute.wepr.ielts_app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@RequestMapping("/paymentHistory")
@Controller
@CrossOrigin
public class PaymentHistoryController {
	
	@GetMapping("/{userId}")
	@IsStudent
	public String paymentHistoryPage(@PathVariable("userId") int userId) {
		
		return "student/payment_history"; 
	}
}
