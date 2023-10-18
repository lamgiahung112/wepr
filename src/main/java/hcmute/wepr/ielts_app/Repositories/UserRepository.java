package hcmute.wepr.ielts_app.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.ApplicationUser;

public interface UserRepository extends JpaRepository<ApplicationUser, Integer>{ 
	Optional<ApplicationUser> findApplicationUserByUsername(String username);
}
