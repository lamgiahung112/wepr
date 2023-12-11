package hcmute.wepr.ielts_app.Services.Interfaces;

import hcmute.wepr.ielts_app.Utilities.Requests.PaymentRequest;

public interface PaypalServiceInterface {
	void createPayment(PaymentRequest request);
}
