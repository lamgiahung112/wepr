package hcmute.wepr.ielts_app.Models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TransactionDetailId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "paypal_transaction_id")
	private String paypalTransactionId;

	@Column(name = "course_id")
	private int courseId;

}
