package hcmute.wepr.ielts_app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}