package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="writing_answer_correction")
public class WritingAnswerCorrection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="writing_answer_correction_id")
    private Long id;

    private String correction;
    private Date createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {
            @JoinColumn(name = "student_writing_answer_exercise_id", referencedColumnName = "writing_exercise_id"),
            @JoinColumn(name = "student_writing_answer_attempt_id", referencedColumnName = "answer_attempt_id"),
    }, foreignKey = @ForeignKey(name="FK_writing_answer_correction_student_writing_answer"))
    private StudentWritingAnswer studentWritingAnswer;
}