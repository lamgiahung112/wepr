package hcmute.wepr.ielts_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Services.Interfaces.PaypalServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.PaymentRequest;

@RequestMapping("/payment")
@Controller
@CrossOrigin
public class PaymentController {
	@Autowired
	private PaypalServiceInterface paypalService;
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> payForCart(@RequestBody PaymentRequest request) {
		paypalService.createPayment(request);
		return ResponseEntity.ok().build();
	}
}
