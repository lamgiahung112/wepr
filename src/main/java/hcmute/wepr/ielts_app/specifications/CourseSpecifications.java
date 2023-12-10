package hcmute.wepr.ielts_app.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.enums.DifficultLevel;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;


public class CourseSpecifications {

	public static Specification<Course> filterCourses(String authors, boolean priceRangeFilter, Float minPrice, Float maxPrice, boolean ratingRangeFilter, Float minRating,
			Float maxRating, Integer minEnrollment, Integer maxEnrollment, String difficulties) {
		return (Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

	        if (authors != null && !authors.isEmpty()) {
	            Join<Course, ApplicationUser> userJoin = root.join("user", JoinType.LEFT);

	            List<Predicate> authorPredicates = new ArrayList<>();
	            String[] authorArray = authors.split(",");

	            for (String author : authorArray) {
	                Predicate authorPredicate = criteriaBuilder.equal(userJoin.get("username"), author.trim());
	                authorPredicates.add(authorPredicate);
	            }

	            Predicate finalAuthorPredicate = criteriaBuilder.or(authorPredicates.toArray(new Predicate[0]));
	            predicates.add(finalAuthorPredicate);
	        }
            
            if (difficulties != null && !difficulties.isEmpty()) {
                List<DifficultLevel> difficultyLevels = List.of(difficulties.split(","))
                        .stream()
                        .map(DifficultLevel::valueOf)
                        .collect(Collectors.toList());
                predicates.add(root.get("level").in(difficultyLevels));
            }
            
            if (priceRangeFilter) {
            	if (minPrice >= 0.0f) {
	                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
	            }
	
	            if (maxPrice >= 0.0f) {
	                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
	            }
            }
	            
            if (ratingRangeFilter) {
            	if (minRating >= 0.0f) {
	                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating));
	            }
	
	            if (maxRating >= 0.0f) {
	                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating));
	            }
            }   

            if (minEnrollment != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("enrolledNumber"), minEnrollment));
            }

            if (maxEnrollment != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("enrolledNumber"), maxEnrollment));
            }

            // Similarly, add other conditions based on your filtering criteria
            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            System.out.println(finalPredicate);

            return finalPredicate;
        };
	}
	
}
