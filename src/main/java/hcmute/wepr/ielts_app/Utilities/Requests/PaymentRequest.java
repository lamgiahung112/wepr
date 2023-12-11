package hcmute.wepr.ielts_app.Utilities.Requests;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentRequest {
	private String paypalOrderId;
	private int userId;
}
