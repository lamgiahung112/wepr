package hcmute.wepr.ielts_app.Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import hcmute.wepr.ielts_app.Models.enums.DifficultLevel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "course_id")
	private int courseId;
	@Column(name = "course_name", columnDefinition = "NVARCHAR(255)")
	private String courseName;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String description;
	@Column(name = "cover_image", columnDefinition = "VARCHAR(255)")
	private String coverImage;
	@Column(name = "created_at", columnDefinition = "DATETIME")
	private LocalDateTime createdAt;
	@Column(name = "updated_at", columnDefinition = "DATETIME")
	private LocalDateTime updatedAt;
	private float price;
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(20)")
	private DifficultLevel level;
	private float rating;
	@Column(name = "enrolled_number")
	private int enrolledNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ApplicationUser user;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<CartItem> cartItems = new HashSet<>();
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<PurchaseTransaction> purchaseTransactions = new HashSet<>();
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Lesson> lessons = new HashSet<>();
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<UserProgress> userProgresses = new HashSet<>();
}
