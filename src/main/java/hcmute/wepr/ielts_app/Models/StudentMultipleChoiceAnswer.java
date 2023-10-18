package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;


@Entity
@Table(name="student_multiple_choice_answer")
public class StudentMultipleChoiceAnswer {
    @EmbeddedId
    private StudentMultipleChoiceAnswerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="multiple_choice_exercise_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name="FK_student_multiple_choice_answer_multiple_choice_exercise"))
    private MultipleChoiceExercise multipleChoiceExercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="answer_attempt_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name="FK_student_multiple_choice_answer_answer_attempt"))
    private AnswerAttempt answerAttempt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exercise_choice_id", foreignKey = @ForeignKey(name="FK_student_multiple_choice_answer_exercise_choice"))
    private ExerciseChoice exerciseChoice;
}