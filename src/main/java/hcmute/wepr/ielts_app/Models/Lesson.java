package hcmute.wepr.ielts_app.Models;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lesson")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lesson_id")
	private int lessonId;
	@Column(columnDefinition = "VARCHAR(255)")
	private String video;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String title;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private WritingExercise writingExercise;
	
	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<UserProgress> userprogresses = new HashSet<>();
}
