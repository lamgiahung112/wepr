package hcmute.wepr.ielts_app.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseTransaction {
	@Id
	@Column(name = "paypal_transaction_id")
	private String paypalTransactionId;
	
	@Column(name = "created_at", columnDefinition = "DATETIME")
	private LocalDateTime createdAt;
	private float amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ApplicationUser user;
	
}
