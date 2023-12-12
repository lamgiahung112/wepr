package hcmute.wepr.ielts_app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.enums.Role;

public interface UserRepositoryInterface extends JpaRepository<ApplicationUser, Integer> {
	Optional<ApplicationUser> findByUsername(String username);
	@Query("SELECT au FROM ApplicationUser au LEFT JOIN FETCH au.profile WHERE au.userId = :userId")
    ApplicationUser findUserWithProfileByUserId(int userId);
	List<ApplicationUser> findWithUserProfileByRole(Role roleTeacher);
	Page<ApplicationUser> findByRoleAndIsEnabled(Role role, boolean enabled, Pageable pageable);
	Page<ApplicationUser> findByRole(Role role, Pageable pageable);
	Page<ApplicationUser> findByIsEnabled(boolean enabled, Pageable pageable);
	Page<ApplicationUser> findAll(Pageable pageable);
}
