package hcmute.wepr.ielts_app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}