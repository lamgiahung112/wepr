package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

@Entity
@Table(name="exercise_choice")
public class ExerciseChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="excercise_choice_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="multiple_choice_exercise_id", foreignKey = @ForeignKey(name="FK_exercise_choice_multiple_choice_exercise"))
    private MultipleChoiceExercise multipleChoiceExercise;

    @OneToOne(mappedBy = "exerciseChoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private StudentMultipleChoiceAnswer studentMultipleChoiceAnswer;
}