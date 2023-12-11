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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.Services.Interfaces.UserServiceInterface;
import hcmute.wepr.ielts_app.Utilities.responses.CourseDTO;
import hcmute.wepr.ielts_app.Utilities.responses.CourseStatisticsResponse;
import hcmute.wepr.ielts_app.Utilities.responses.FilteredCourseResponse;
import hcmute.wepr.ielts_app.Utilities.responses.TeacherNameDTO;

@Controller
@CrossOrigin
@RequestMapping("/courses")
public class CourseController {
	@Autowired
	CourseServiceInterface courseService;
	@Autowired
	UserServiceInterface userService;
	
	@GetMapping
	public String coursePage() {
		return "courses";
	}
	
	@PostMapping("/rating")
	@ResponseBody
	public ResponseEntity<?> rateCourse(@RequestBody RateCourseRequest request) {
		courseService.rateCourse(request);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/getTeacherName") 
	@ResponseBody
	public ResponseEntity<List<TeacherNameDTO>> getAllTeacherNameAndUsername(){
		List<TeacherNameDTO> teacherNames = userService.getTeacherNameAndUsername();
		return ResponseEntity.status(HttpStatus.OK).body(teacherNames);
	}
	
	@GetMapping("/{courseId}")
	public String getCourseDetail(Authentication authentication, Model model,@PathVariable(name = "courseId") int courseId) {
		Integer userid = Integer.valueOf(authentication.getCredentials().toString());
		System.out.println(userid);
		Course course = courseService.findCourseWithLessonsAndWithUserByCourseId(courseId);
		ApplicationUser author = userService.findWithUserProfileById(course.getUser().getUserId());
		CourseStatisticsResponse stats = courseService.getCourseStatistics(courseId);
		UserProgress progress = null;
		if (userid != null) {
			progress = courseService.getUserCourseProgress(userid, courseId);
		}

		model.addAttribute("course", course);
		model.addAttribute("author", author.getProfile().getName());
		model.addAttribute("stats", stats);
		model.addAttribute("lessons", course.getLessons());
		model.addAttribute("userId", userid);
		model.addAttribute("hasBoughtCourse", progress != null);
		
		return "course_details";
	}
	
	@GetMapping("/find")
	@ResponseBody
	public ResponseEntity<FilteredCourseResponse> getCourses(
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
