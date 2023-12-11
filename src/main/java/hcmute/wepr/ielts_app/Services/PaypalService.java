package hcmute.wepr.ielts_app.Services;

import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Services.Interfaces.PaypalServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.PaymentRequest;

@Service
public class PaypalService implements PaypalServiceInterface {

	@Override
	public void createPayment(PaymentRequest request) {
		
	}

}
