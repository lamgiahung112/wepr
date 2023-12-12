package hcmute.wepr.ielts_app.Models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "writing_exercise")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WritingExercise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int exerciseId;
	
	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String title;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_id")
	private Lesson lesson;
	
	@OneToMany(mappedBy = "writingExercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<StudentWritingAnswer> studentWritingAnswer = new HashSet<>();
}
