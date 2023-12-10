package hcmute.wepr.ielts_app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.enums.Role;

public interface UserRepositoryInterface extends JpaRepository<ApplicationUser, Integer> {
	Optional<ApplicationUser> findByUsername(String username);
	@Query("SELECT au FROM ApplicationUser au LEFT JOIN FETCH au.profile WHERE au.userId = :userId")
    ApplicationUser findUserWithProfileByUserId(int userId);
	List<ApplicationUser> findWithUserProfileByRole(Role roleTeacher);
}
