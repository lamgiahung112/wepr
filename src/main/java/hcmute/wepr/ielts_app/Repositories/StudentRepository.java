package hcmute.wepr.ielts_app.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByUsername(String username);
}
