package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hcmute.wepr.ielts_app.Models.UserProfile;

public interface UserProfileRepositoryInterface extends JpaRepository<UserProfile, Integer> {
	UserProfile findByUserUserId (int userid);
}
