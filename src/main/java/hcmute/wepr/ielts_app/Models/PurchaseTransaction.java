package hcmute.wepr.ielts_app.Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
	
	@OneToMany(mappedBy = "purchaseTransaction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<TransactionDetail> transactionDetails = new HashSet<>();
	
}
