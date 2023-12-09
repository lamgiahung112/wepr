package hcmute.wepr.ielts_app.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hcmute.wepr.ielts_app.Models.Course;

@Controller
@CrossOrigin
@RequestMapping("/courses")
public class CourseController {
	@GetMapping
	public String coursePage() {
		return "courses";
	}
	
	@PostMapping
	public List<Course> getCourses(
	        @RequestParam(value = "authors", required = false) String authors,
	        @RequestParam(value = "minPrice", required = false) Double minPrice,
	        @RequestParam(value = "maxPrice", required = false) Double maxPrice,
	        @RequestParam(value = "minRating", required = false) Integer minRating,
	        @RequestParam(value = "maxRating", required = false) Integer maxRating,
	        @RequestParam(value = "minEnrollment", required = false) Integer minEnrollment,
	        @RequestParam(value = "maxEnrollment", required = false) Integer maxEnrollment,
	        @RequestParam(value = "difficulties", required = false) String difficulties,
	        @RequestParam(value = "name", required = false) String nameSorting,
	        @RequestParam(value = "price", required = false) String priceSorting,
	        @RequestParam(value = "rating", required = false) String ratingSorting,
	        @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage
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
	    return null;
	}
}
