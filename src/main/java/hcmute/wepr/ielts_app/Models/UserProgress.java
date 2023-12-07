package hcmute.wepr.ielts_app.Models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_progress")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProgress {
	@EmbeddedId
	private UserProgressId userProgressId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_id")
	private Lesson lesson;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private ApplicationUser user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("courseId")
	@JoinColumn(name = "course_id")
	private Course course;
}
