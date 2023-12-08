package hcmute.wepr.ielts_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hcmute.wepr.ielts_app.Models.enums.Role;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;

@SpringBootApplication
public class IeltsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(IeltsAppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserServiceInterface userService) {
		return args -> {
			//userService.createUser("tin", "tin123", Role.ROLE_TEACHER, "votrongtin882003@gmail.com", 0);
		};
	}
}
