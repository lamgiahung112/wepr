package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StudentWritingAnswerId implements Serializable {
    @Column(name="writing_exercise_id")
    private Long writngExerciseId;
    @Column(name="answer_attempt_id")
    private Long attemptId;
}