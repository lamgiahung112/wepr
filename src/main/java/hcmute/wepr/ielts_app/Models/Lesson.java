package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="lesson_id")
    private Long id;
    private String title;
    private String description;
    private String video;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", foreignKey = @ForeignKey(name="FK_lesson_course"))
    private Course course;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<MultipleChoiceExercise> multipleChoiceExercises;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<WritingExercise> writingExercises;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<AnswerAttempt> answerAttempts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}