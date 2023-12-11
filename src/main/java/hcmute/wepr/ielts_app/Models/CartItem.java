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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItem {
	@EmbeddedId
	private CartItemId cartItemId;
	
	@Column(name = "created_at", columnDefinition = "DATETIME")
	private LocalDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private ApplicationUser user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("courseId")
	@JoinColumn(name = "course_id")
	private Course course;
	
}
