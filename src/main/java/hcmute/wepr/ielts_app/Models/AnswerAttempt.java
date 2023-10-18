package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="answer_attempt")
public class AnswerAttempt {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name="answer_attempt_id")
    private Long id;
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lesson_id", foreignKey = @ForeignKey(name="FK_answer_attempt_lesson"))
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", foreignKey = @ForeignKey(name = "FK_answer_attempt_student"))
    private Student student;

    @OneToMany(mappedBy = "answerAttempt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<StudentWritingAnswer> studentWritingAnswers;

    @OneToMany(mappedBy = "answerAttempt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<StudentMultipleChoiceAnswer> studentMultipleChoiceAnswers;
}