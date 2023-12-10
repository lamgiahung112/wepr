package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import hcmute.wepr.ielts_app.Models.Course;

public interface CourseRepositoryInterface extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

}
