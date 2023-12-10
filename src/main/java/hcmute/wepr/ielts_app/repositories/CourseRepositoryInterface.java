package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.Course;

public interface CourseRepositoryInterface extends JpaRepository<Course, Integer> {

}
