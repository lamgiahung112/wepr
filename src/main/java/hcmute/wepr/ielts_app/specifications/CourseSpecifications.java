package hcmute.wepr.ielts_app.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.enums.DifficultLevel;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;


public class CourseSpecifications {

	public static Specification<Course> filterCourses(String authors, Float minPrice, Float maxPrice, Float minRating,
			Float maxRating, Integer minEnrollment, Integer maxEnrollment, String difficulties) {
		return (Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (authors != null && !authors.isEmpty()) {
                predicates.add(root.join("user").get("username").in(authors));
            }
            
            if (difficulties != null && !difficulties.isEmpty()) {
                List<DifficultLevel> difficultyLevels = List.of(difficulties.split(","))
                        .stream()
                        .map(DifficultLevel::valueOf)
                        .collect(Collectors.toList());
                predicates.add(root.get("level").in(difficultyLevels));
            }
            
            if (minPrice >= 0.0f) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice >= 0.0f) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            
            if (minRating >= 0.0f) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating));
            }

            if (maxRating >= 0.0f) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating));
            }

            if (minEnrollment != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("enrolledNumber"), minEnrollment));
            }

            if (maxEnrollment != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("enrolledNumber"), maxEnrollment));
            }

            // Similarly, add other conditions based on your filtering criteria

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
	}
	
}
