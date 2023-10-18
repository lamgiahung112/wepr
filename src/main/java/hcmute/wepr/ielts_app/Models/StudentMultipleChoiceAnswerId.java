package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StudentMultipleChoiceAnswerId implements Serializable {
    @Column(name="multiple_choice_exercise_id")
    private Long multipleChoiceExerciseId;
    @Column(name="answer_attempt_id")
    private Long attemptId;
}