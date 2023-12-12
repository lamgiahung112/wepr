package hcmute.wepr.ielts_app.Models;


import java.time.LocalDateTime;

import hcmute.wepr.ielts_app.Models.enums.StudentAnswerStatus;import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "student_writing_answer_id")
	private int studentWritingAnswerId;
	
	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String answer;
	
	@Column(name = "created_at", columnDefinition = "DATETIME")
	private LocalDateTime createdAt;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(10)")
	private StudentAnswerStatus studentAnswerStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ApplicationUser user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writing_exercise_id")
	private WritingExercise writingExercise;
	
	@OneToOne(mappedBy = "studentWritingAnswer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private WritingAnswerCorrection writingAnswerCorrection;
}
