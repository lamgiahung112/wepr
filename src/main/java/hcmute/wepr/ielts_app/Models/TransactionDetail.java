package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_detail")
public class TransactionDetail {
	@EmbeddedId
	private TransactionDetailId transactionDetailId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("paypalTransactionId")
	@JoinColumn(name = "paypal_transaction_id")
	private PurchaseTransaction purchaseTransaction;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("courseId")
	@JoinColumn(name = "course_id")
	private Course course;
	
}
