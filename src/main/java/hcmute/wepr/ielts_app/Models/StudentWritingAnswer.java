package hcmute.wepr.ielts_app.Models;


import hcmute.wepr.ielts_app.Models.enums.StudentAnswerStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "student_writing_answer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentWritingAnswer {
	@EmbeddedId
	private StudentWritingAnswerId studentWritingAnswerId;
	
	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String answer;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(10)")
	private StudentAnswerStatus studentAnswerStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private ApplicationUser user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("writingExerciseId")
	@JoinColumn(name = "writing_exercise_id")
	private WritingExercise writingExercise;
	
	@OneToOne(mappedBy = "studentWritingAnswer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private WritingAnswerCorrection writingAnswerCorrection;
}
