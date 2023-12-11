package hcmute.wepr.ielts_app.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.Utilities.responses.CourseDTO;

@Controller
@CrossOrigin
@RequestMapping("/courses")
public class CourseController {
	@Autowired
	CourseServiceInterface courseService;
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
	
	@GetMapping("/find")
	@ResponseBody
	public ResponseEntity<List<CourseDTO>> getCourses(
	        @RequestParam(value = "authors", required = false) String authors,
	        @RequestParam(value = "minPrice", required = false) Float minPrice,
	        @RequestParam(value = "maxPrice", required = false) Float maxPrice,
	        @RequestParam(value = "minRating", required = false) Float minRating,
	        @RequestParam(value = "maxRating", required = false) Float maxRating,
	        @RequestParam(value = "minEnrollment", required = false) Integer minEnrollment,
	        @RequestParam(value = "maxEnrollment", required = false) Integer maxEnrollment,
	        @RequestParam(value = "difficulties", required = false) String difficulties,
	        @RequestParam(value = "name", defaultValue = "asc") String nameSorting,
	        @RequestParam(value = "price", defaultValue = "asc") String priceSorting,
	        @RequestParam(value = "rating", defaultValue = "asc") String ratingSorting,
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
		
		if (minPrice == null) {
			minPrice = 0.0f;
		}
		
		if (maxPrice == null) {
			maxPrice = 0.0f;
		}
		
		if (minRating == null) {
			minRating = 0.0f;
		}
		
		if (maxRating == null) {
			maxRating = 0.0f;
		}
		
		List<Course> courses = courseService.getCourseWithSpecAndPaging(authors, difficulties, minPrice, maxPrice, minRating, maxRating, minEnrollment, maxEnrollment, nameSorting, priceSorting, ratingSorting, itemsPerPage, page);
		
		// Convert Course objects to CourseDTO objects
        List<CourseDTO> courseDTOs = courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());

        // Return CourseDTO objects as JSON
        return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
	}
	
	private CourseDTO convertToCourseDTO(Course course) {
        return new CourseDTO(
                course.getCourseId(),
                course.getCourseName(),
                course.getCoverImage(),
                course.getCreatedAt(),
                course.getPrice(),
                course.getRating(),
                course.getEnrolledNumber()
        );
    }
}
