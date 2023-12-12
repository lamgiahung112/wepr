package hcmute.wepr.ielts_app.Models;

import java.util.HashSet;
import java.util.Set;

import hcmute.wepr.ielts_app.Models.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", unique = true)
	private int userId;
	
	@Column(columnDefinition = "VARCHAR(60)")
	private String username;
	@Column(name = "hash_password", columnDefinition = "VARCHAR(255)")
	private String hashPassword;
	@Column(columnDefinition = "VARCHAR(60)")
	private String email;
	private double balance;
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(30)")
	private Role role;
	@Column(columnDefinition = "BIT")
	private boolean isEnabled;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private UserProfile profile;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<CartItem> cartItems = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Course> courses = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<PurchaseTransaction> purchaseTransactions = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<UserProgress> userProgresses = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<StudentWritingAnswer> studentWritingAnswer = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Rating> ratings = new HashSet<>();

}
