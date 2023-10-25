package hcmute.wepr.ielts_app.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.Student;
import hcmute.wepr.ielts_app.Repositories.StudentRepository;
import hcmute.wepr.ielts_app.Services.Interfaces.StudentServiceInterface;
import hcmute.wepr.ielts_app.Utilities.JwtDataWrapper;
import hcmute.wepr.ielts_app.Utilities.JwtUtils;
import hcmute.wepr.ielts_app.Utilities.Requests.SignUpUserRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService implements StudentServiceInterface {
	private final String studentRole = "ROLE_STUDENT";
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public String authenticateAndGetCredentials(String username, String password) {
		Optional<Student> student = studentRepository.findByUsername(username);
		
		if (student.isEmpty()) {
			return null;
		}
		
		boolean isPasswordMatched = passwordEncoder.matches(password, student.get().getPasswordHash());
		if (!isPasswordMatched) {
			return null;
		}
		
		JwtDataWrapper data = JwtDataWrapper.builder()
				.id(student.get().getId().toString())
				.name(student.get().getStudentName())
				.username(student.get().getUsername())
				.role(studentRole)
				.build();
		return jwtUtils.generateToken(data);
	}

	@Override
	public void createNewStudent(SignUpUserRequest request) {
		String hashedPw = passwordEncoder.encode(request.getPassword());
		Student std = Student.builder()
				.balance(0)
				.email(request.getEmail())
				.studentName(request.getName())
				.username(request.getUsername())
				.passwordHash(hashedPw)
				.build();
		studentRepository.save(std);
	}

}
