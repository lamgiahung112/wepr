package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_profile")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "profile_id")
	private int profileId;
	@Column(columnDefinition = "NVARCHAR(50)")
	private String name;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String address;
	@Column(name = "phone_number", columnDefinition = "VARCHAR(10)")
	private String phoneNumber;
	@Column(name = "cv_link", columnDefinition = "VARCHAR(255)")
	private String cvLink;
	@Column(name = "exp_description", columnDefinition = "NVARCHAR(255)")
	private String experienceDescription;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ApplicationUser user;
	
}
