package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.Lesson;

public interface LessonRepositoryInterface extends JpaRepository<Lesson, Integer> {

}
