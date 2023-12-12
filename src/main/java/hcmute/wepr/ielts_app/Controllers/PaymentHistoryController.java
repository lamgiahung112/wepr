package hcmute.wepr.ielts_app.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.wepr.ielts_app.Models.PurchaseTransaction;
import hcmute.wepr.ielts_app.Services.Interfaces.PurchaseTransactionServiceInteface;
import hcmute.wepr.ielts_app.security.annotations.IsStudent;

@RequestMapping("/paymentHistory")
@Controller
@CrossOrigin
public class PaymentHistoryController {
	@Autowired
	private PurchaseTransactionServiceInteface purchaseTransactionService;
	@GetMapping("/{userId}")
	@IsStudent
	public String paymentHistoryPage(Model model, @PathVariable("userId") int userId) {
		List<PurchaseTransaction> purchaseTransactions = purchaseTransactionService.getFullDetailsPurchaseTransactions(userId);
		PurchaseTransaction purchaseTransaction = purchaseTransactionService.getLastestPurchaseTransaction(userId);
		model.addAttribute("purchaseTransactions", purchaseTransactions);
		model.addAttribute("latestTransaction", purchaseTransaction);
		return "student/payment_history"; 
	}
	

}
