package hcmute.wepr.ielts_app.Models;

import java.time.LocalDateTime;
import java.util.Set;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "writing_answer_correction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WritingAnswerCorrection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "writing_answer_correction_id")
	private int writingAnswerCorrectionId;
	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String correction;
	@Column(name = "created_at", columnDefinition = "DATETIME")
	private LocalDateTime createdAt;
	
	@OneToOne(fetch = FetchType.LAZY)
	private StudentWritingAnswer studentWritingAnswer;
}
