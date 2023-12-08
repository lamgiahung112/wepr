package hcmute.wepr.ielts_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import hcmute.wepr.ielts_app.Models.ApplicationUser;

public interface UserRepositoryInterface extends JpaRepository<ApplicationUser, Integer> {
	Optional<ApplicationUser> findByUsername(String username);
}
