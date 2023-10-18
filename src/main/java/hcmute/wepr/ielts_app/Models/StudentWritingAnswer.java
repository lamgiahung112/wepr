package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

@Entity
@Table(name="student_writing_answer")
public class StudentWritingAnswer {
    @EmbeddedId
    private StudentWritingAnswerId id;

    private String answer;
    private int status;

    @ManyToOne
    @JoinColumn(name="writing_exercise_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_student_writing_answer_writing_exercise"))
    private WritingExercise writingExercise;

    @ManyToOne
    @JoinColumn(name="answer_attempt_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_student_writing_answer_answer_attempt"))
    private AnswerAttempt answerAttempt;

    @OneToOne(mappedBy = "studentWritingAnswer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private WritingAnswerCorrection writingAnswerCorrection;
}