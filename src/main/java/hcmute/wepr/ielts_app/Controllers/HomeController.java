package hcmute.wepr.ielts_app.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserProgressServiceInterface;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.responses.CourseDTO;
import hcmute.wepr.ielts_app.Utilities.responses.FilteredCourseResponse;
import hcmute.wepr.ielts_app.Utilities.responses.TeacherNameDTO;
import hcmute.wepr.ielts_app.repositories.UserProfileRepositoryInterface;

@Controller
@CrossOrigin
@RequestMapping("/home")
public class HomeController {
	@Autowired
	CourseServiceInterface courseService;
	@Autowired
	UserServiceInterface userService;
	@Autowired
	private UserProgressServiceInterface userProgressRepository;
	
	@GetMapping
	public String homePage(Authentication authentication, Model model) {
		// Kiểm tra xem người dùng đã đăng nhập hay không
	    if (authentication != null && authentication.isAuthenticated()) {
	        // Lấy thông tin người dùng từ đối tượng Authentication
	        String username = authentication.getName();
	        // Gửi thông tin người dùng tới View
	        model.addAttribute("username", username);
	    }
		return "home";
	}
	@GetMapping("/courses")
	public String coursePage(Authentication authentication, Model model) {
		if (authentication != null && authentication.isAuthenticated()) {
	        // Lấy thông tin người dùng từ đối tượng Authentication
	        String username = authentication.getName();
	        // Gửi thông tin người dùng tới View
	        model.addAttribute("username", username);
	    }
		return "courses";
	}
	
	@GetMapping("/courses/getTeacherName") 
	@ResponseBody
	public ResponseEntity<List<TeacherNameDTO>> getAllTeacherNameAndUsername(){
		List<TeacherNameDTO> teacherNames = userService.getTeacherNameAndUsername();
		return ResponseEntity.status(HttpStatus.OK).body(teacherNames);
	}
	
	@GetMapping("/courses/find")
	@ResponseBody
	public ResponseEntity<FilteredCourseResponse> getCourses(
			Authentication auth,
			@RequestParam(value = "isBought", required = false) Boolean isBought,
	        @RequestParam(value = "authors", required = false) String authors,
	        @RequestParam(value = "minPrice", required = false) Float minPrice,
	        @RequestParam(value = "maxPrice", required = false) Float maxPrice,
	        @RequestParam(value = "minRating", required = false) Float minRating,
	        @RequestParam(value = "maxRating", required = false) Float maxRating,
	        @RequestParam(value = "minEnrollment", required = false) Integer minEnrollment,
	        @RequestParam(value = "maxEnrollment", required = false) Integer maxEnrollment,
	        @RequestParam(value = "difficulties", required = false) String difficulties,
	        @RequestParam(value = "name", defaultValue = "nosort") String nameSorting,
	        @RequestParam(value = "price", defaultValue = "nosort") String priceSorting,
	        @RequestParam(value = "rating", defaultValue = "nosort") String ratingSorting,
	        @RequestParam(value = "itemsPerPage", defaultValue = "0") Integer itemsPerPage,
	        @RequestParam(value = "page", defaultValue = "0") Integer page
	) {
		System.out.println(authors);
		System.out.println(minPrice);
		System.out.println(maxPrice);
		System.out.println(minRating);
		System.out.println(maxRating);
		System.out.println(minEnrollment);
		System.out.println(maxEnrollment);
		System.out.println(difficulties);
		System.out.println(nameSorting);
		System.out.println(priceSorting);
		System.out.println(ratingSorting);
		System.out.println(itemsPerPage);
		
		boolean priceRangeFilter = true;
		boolean ratingRangeFilter = true;
		
		if (minPrice == null) {
			minPrice = 0.0f;
			priceRangeFilter = false;
		}
		
		if (maxPrice == null) {
			priceRangeFilter = false;
			maxPrice = 0.0f;
		}
		
		if (minRating == null) {
			minRating = 0.0f;
			ratingRangeFilter = false;
		}
		
		if (maxRating == null) {
			maxRating = 0.0f;
			ratingRangeFilter = false;
		}
		
		List<Course> courses = courseService.getCourseWithSpecAndPaging(authors, difficulties, priceRangeFilter, minPrice, maxPrice, ratingRangeFilter, minRating, maxRating, minEnrollment, maxEnrollment, nameSorting, priceSorting, ratingSorting, itemsPerPage, page);
		Long totalResultNumber = courseService.countCourseWithSpecAndPaging(authors, difficulties, priceRangeFilter, minPrice, maxPrice, ratingRangeFilter, minRating, maxRating, minEnrollment, maxEnrollment, nameSorting, priceSorting, ratingSorting, itemsPerPage, page);
		
		if (isBought != null && auth != null && auth.getCredentials() != null) {
			int userId = Integer.valueOf(auth.getCredentials().toString());
			courses = courses.stream().filter(course -> {
				UserProgress progress = userProgressRepository.findByUserProgressId(userId, course.getCourseId());
				return isBought ? progress != null : progress == null;
			}).toList();
		}
		
		// Convert Course objects to CourseDTO objects
        List<CourseDTO> courseDTOs = courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());

        // Return CourseDTO objects as JSON
        return ResponseEntity.status(HttpStatus.OK).body(new FilteredCourseResponse(courseDTOs, totalResultNumber));
	}
	
	private CourseDTO convertToCourseDTO(Course course) {
	    CourseDTO courseDTO = new CourseDTO();
	    courseDTO.setCourseId(course.getCourseId());
	    courseDTO.setCourseName(course.getCourseName());
	    courseDTO.setCoverImage(course.getCoverImage());
	    courseDTO.setCreatedAt(course.getCreatedAt());
	    courseDTO.setPrice(course.getPrice());
	    courseDTO.setRating(course.getRating());
	    courseDTO.setEnrolledNumber(course.getEnrolledNumber());
	    
	    // Assuming author is retrieved from the associated ApplicationUser
	    if (course.getUser() != null) {
	        courseDTO.setAuthor(course.getUser().getUsername());
	    } else {
	        courseDTO.setAuthor(""); // Set default value if author is not present
	    }

	    // Assuming difficulty is retrieved from the DifficultLevel enum
	    courseDTO.setDifficulty(course.getLevel().toString());

	    return courseDTO;
	}
}
