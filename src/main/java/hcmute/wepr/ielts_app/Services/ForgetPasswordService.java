package hcmute.wepr.ielts_app.Services;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Services.Interfaces.ForgetPasswordServiceInterface;
import hcmute.wepr.ielts_app.Utilities.CacheItem;
import hcmute.wepr.ielts_app.repositories.UserRepositoryInterface;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ForgetPasswordService implements ForgetPasswordServiceInterface {
	Map<String, CacheItem> cache = new HashMap<>();
	
	@Autowired
	private UserRepositoryInterface userRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void sendResetPasswordEmail(String username) throws MessagingException {
		String uuid = UUID.randomUUID().toString();
				
		ApplicationUser user = userRepository.findByUsername(username).orElse(null);
		cache.put(uuid, CacheItem.builder()
				.expirationTime(System.currentTimeMillis() + 600 * 1000)
				.user(user)
				.build()
		);
		String link = "localhost:8080/auth/reset?requestId=" + uuid;
		String to = user.getEmail();
		String subject = "Reset password";
		String body = link;
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());
		
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body);
		javaMailSender.send(msg);
	}

	@Override
	public boolean isValidRequest(String requestId) {
		return cache.containsKey(requestId) && System.currentTimeMillis() < cache.get(requestId).getExpirationTime();
	}

	@Override
	public ApplicationUser getUserOfRequest(String requestId) {
		return cache.get(requestId).getUser();
	}

	@Override
	public void changePassword(String requestId, String password) {
		ApplicationUser user = getUserOfRequest(requestId);
		
		user.setHashPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		cache.remove(requestId);
	}

}
