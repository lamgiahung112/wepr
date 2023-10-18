package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="multiple_choice_exercise")
public class MultipleChoiceExercise {
    @Id
    @GeneratedValue
    @Column(name="multiple_choice_exercise_id")
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lesson_id", foreignKey = @ForeignKey(name = "FK_multiple_choice_exercise_lesson"))
    private Lesson lesson;

    @OneToMany(mappedBy = "multipleChoiceExercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<ExerciseChoice> exerciseChoices;

    @OneToMany(mappedBy = "multipleChoiceExercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<StudentMultipleChoiceAnswer> studentMultipleChoiceAnswers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="exercise_correct_answer",
            joinColumns={@JoinColumn(name="exercise_id")},
            inverseJoinColumns={@JoinColumn(name="choice_id")}
    )
    private Set<ExerciseChoice> exerciseCorrectAnswer;
}