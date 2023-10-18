package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="writing_exercise")
public class WritingExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="writing_exercise_id")
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lesson_id", foreignKey = @ForeignKey(name = "FK_writing_exercise_lesson"))
    private Lesson lesson;

    @OneToMany(mappedBy = "writingExercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<StudentWritingAnswer> studentWritingAnswers;

}