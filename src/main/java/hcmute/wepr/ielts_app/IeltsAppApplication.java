package hcmute.wepr.ielts_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hcmute.wepr.ielts_app.Models.enums.Role;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;

@SpringBootApplication
public class IeltsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(IeltsAppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserServiceInterface userService, CourseServiceInterface courseService) {
		return args -> {
			userService.createUser("tinstudent", "tin123", Role.ROLE_STUDENT, "votrongtin882003@gmail.com", 0);
			userService.createUser("tin", "tin123", Role.ROLE_TEACHER, "votrongtin882003@gmail.com", 0);
			userService.createUser("teacher1", "password", Role.ROLE_TEACHER, "teacher1@example.com", 0);
			userService.createUser("teacher2", "password", Role.ROLE_TEACHER, "teacher2@example.com", 0);
			userService.createUser("teacher3", "password", Role.ROLE_TEACHER, "teacher3@example.com", 0);
			userService.createUser("teacher4", "password", Role.ROLE_TEACHER, "teacher4@example.com", 0);
			userService.createUser("teacher5", "password", Role.ROLE_TEACHER, "teacher5@example.com", 0);
			userService.createUser("teacher6", "password", Role.ROLE_TEACHER, "teacher6@example.com", 0);
			userService.createUser("teacher7", "password", Role.ROLE_TEACHER, "teacher7@example.com", 0);
			userService.createUser("teacher8", "password", Role.ROLE_TEACHER, "teacher8@example.com", 0);
			userService.createUser("teacher9", "password", Role.ROLE_TEACHER, "teacher9@example.com", 0);
			userService.createUser("teacher10", "password", Role.ROLE_TEACHER, "teacher10@example.com", 0);
			
			//courseService.generatesCourses();
		};
	}
}
