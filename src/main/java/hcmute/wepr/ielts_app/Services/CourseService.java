package hcmute.wepr.ielts_app.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Models.Rating;
import hcmute.wepr.ielts_app.Models.RatingId;
import hcmute.wepr.ielts_app.Models.UserProgress;
import hcmute.wepr.ielts_app.Models.UserProgressId;
import hcmute.wepr.ielts_app.Models.enums.DifficultLevel;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.BuyCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.RateCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateCourseRequest;
import hcmute.wepr.ielts_app.Utilities.responses.CourseStatisticsResponse;
import hcmute.wepr.ielts_app.repositories.CourseRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.LessonRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.RatingRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserProfileRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserProgressRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.UserRepositoryInterface;
import hcmute.wepr.ielts_app.specifications.CourseSpecifications;

@Service
public class CourseService implements CourseServiceInterface {
	@Autowired
	private CourseRepositoryInterface courseRepository;
	@Autowired
	private LessonRepositoryInterface lessonRepository;

	@Autowired
	private UserRepositoryInterface userRepository;
	
	@Autowired
	private UserProfileRepositoryInterface userProfileRepository;
	
	@Autowired
	private RatingRepositoryInterface ratingRepository;
	
	@Autowired
	private UserProgressRepositoryInterface userProgressRepository;

	@Override
	public Course createNewCourse(CreateNewCourseRequest request) {
		ApplicationUser teacher = userRepository.findById(request.getUserId()).orElse(null);
		Course course = Course.builder().courseName(request.getCourseName()).description(request.getCourseDescription())
				.coverImage(request.getCoverImageLink()).level(request.getDifficultyLevel()).price(request.getPrice())
				.createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).user(teacher).build();

		Course savedCourse = courseRepository.save(course);
		if (savedCourse == null)
			return null;

		request.getLessons().stream().forEach(lesson -> {
			Lesson toBeSavedLesson = Lesson.builder().description(lesson.getDescription()).title(lesson.getTitle())
					.video(lesson.getVideoLink()).course(savedCourse).build();
			lessonRepository.save(toBeSavedLesson);
		});
		return savedCourse;
	}

	@Override
	public Course updateCourse(UpdateCourseRequest request) {
		Course course = courseRepository.findCourseWithLessonsByCourseId(request.getCourseId());
		
		course
			.setCourseName(request.getCourseName())
			.setCoverImage(request.getCoverImageLink())
			.setDescription(request.getCourseDescription())
			.setLevel(request.getDifficultyLevel())
			.setPrice(request.getPrice())
			.setUpdatedAt(LocalDateTime.now());
		
		lessonRepository.deleteAllInBatch(course.getLessons());
		
		request.getLessons().stream().forEach(
				lesson -> {
					Lesson toBeSavedLesson = Lesson.builder()
							.description(lesson.getDescription())
							.title(lesson.getTitle())
							.video(lesson.getVideoLink())
							.course(course)
							.build();
					lessonRepository.save(toBeSavedLesson);
				}
		);
		
		return courseRepository.save(course);
	}

	@Override
	public Course findCourseWithLessonsByCourseId(int courseId) {
		return courseRepository.findCourseWithLessonsByCourseId(courseId);
	}
	public void generatesCourses() {
		ApplicationUser[] teachers = new ApplicationUser[10];
		for (int i = 1; i <= 10; i++) {
			Optional<ApplicationUser> user = userRepository.findByUsername("teacher" + i);
			teachers[i - 1] = user.get();
		}
		String[] courseNames = { "English for Beginners", "Advanced Grammar", "IELTS Speaking Mastery",
				"Writing Workshop", "TOEFL Preparation", "Pronunciation Practice", "Business English",
				"Reading Comprehension", "Conversation Skills", "Language Proficiency Test", "English Literature",
				"Creative Writing", "Public Speaking", "Technical Writing", "English for Academic Purposes",
				"English Vocabulary Expansion", "Professional Email Writing", "Language Proficiency Exam Prep",
				"English for Travelers", "English Pronunciation Masterclass", "Effective Communication Skills",
				"Grammar Refresher Course", "English for Job Interviews", "English for Business Communication",
				"Presentation Skills Workshop", "TOEIC Exam Prep", "English Speaking Club",
				"Effective Writing Strategies", "English for Healthcare Professionals", "Academic Writing Essentials",
				"English for Customer Service", "English for Sales Professionals", "English for Engineers",
				"Advanced English Composition", "Media English", "English for Legal Professionals",
				"English for Hospitality Industry", "English for Social Media", "Technical English",
				"English for Marketing", "Business Correspondence in English", "Business English Negotiation Skills",
				"English Language Proficiency Program"
				// Add more course names here if needed
		};

		String[] courseDescriptions = { "This course covers basic English skills for beginners.",
				"Advanced grammar concepts for fluent English communication.",
				"Master the speaking module of the IELTS exam.",
				"Improve your writing skills with interactive workshops.",
				"Prepare for the TOEFL exam with comprehensive lessons.", "Practice and perfect English pronunciation.",
				"Learn English for business and professional environments.",
				"Enhance reading comprehension skills in English.", "Improve your conversational skills in English.",
				"Prepare for language proficiency tests with this course.",
				"Dive into the world of English literature.", "Learn the art of creative writing in English.",
				"Develop confidence in public speaking.", "Enhance your technical writing abilities.",
				"Get equipped with English skills for academic purposes.", "Expand your English vocabulary.",
				"Master the art of writing professional emails in English.", "Prepare for language proficiency exams.",
				"Learn English phrases for travelers.", "Achieve mastery in English pronunciation.",
				"Learn effective communication strategies.", "Refresh your knowledge of English grammar.",
				"Prepare for job interviews in English.", "Improve English skills for business communication.",
				"Work on your presentation skills.", "Get ready for the TOEIC exam.",
				"Join a club to enhance English speaking.", "Learn effective writing strategies.",
				"English language skills for healthcare professionals.", "Master the essentials of academic writing.",
				"English communication for customer service roles.", "English skills for sales professionals.",
				"English for engineers and technical professionals.", "Advanced course on English composition.",
				"Learn English for media and communications.", "English language skills for legal professionals.",
				"English for the hospitality industry.", "Learn English for social media usage.",
				"Specialized course on technical English.", "English for marketing professionals.",
				"Learn business correspondence in English.", "Enhance negotiation skills in English.",
				"Comprehensive program for English language proficiency."
				// Add more course descriptions here if needed
		};

		DifficultLevel[] difficultLevels = DifficultLevel.values();

		for (int i = 1; i <= 200; i++) { // Generate 200 courses
			Course course = Course.builder().courseName(courseNames[i % courseNames.length])
					.description(courseDescriptions[i % courseDescriptions.length])
					.coverImage("image" + (i % 10) + ".jpg").createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now()).price((float) Math.floor((Math.random() * 450) + 50)) // Random
																											// price
																											// between
																											// 50 and
																											// 500
					.level(difficultLevels[i % difficultLevels.length])
					.rating((float) Math.floor((Math.random() * 5) + 1)) // Random rating between 1 and 5
					.enrolledNumber((int) Math.floor(Math.random() * 1000)) // Random number of enrollments
					.user(teachers[i % 10]).build();

			courseRepository.save(course);
		}
	}

	@Override
	public List<Course> getCourseWithSpecAndPaging(String authors, String difficulties, boolean priceRangeFilter, float minPrice, float maxPrice,
			boolean ratingRangeFilter, float minRating, float maxRating, Integer minEnrollment, Integer maxEnrollment, String nameSorting,
			String priceSorting, String ratingSorting, Integer itemsPerPage, Integer page) {
		// Create Pageable object for pagination
	    Pageable pageable = PageRequest.of(page, itemsPerPage);

	    // Create Specification based on filtering criteria
	    Specification<Course> spec = CourseSpecifications.filterCourses(authors, priceRangeFilter, minPrice, maxPrice,
	            ratingRangeFilter, minRating, maxRating, minEnrollment, maxEnrollment, difficulties);

	    Sort sort = null;

	    // Add sorting criteria for 'name' if 'nameSorting' is not "nosort"
	    if (!"nosort".equalsIgnoreCase(nameSorting)) {
	        Sort.Order nameOrder = "asc".equalsIgnoreCase(nameSorting) ? Sort.Order.asc("courseName") : Sort.Order.desc("courseName");
	        sort = Sort.by(nameOrder);
	    }

	    // Add sorting criteria for 'price' if 'priceSorting' is not "nosort"
	    if (!"nosort".equalsIgnoreCase(priceSorting)) {
	        Sort.Order priceOrder = "asc".equalsIgnoreCase(priceSorting) ? Sort.Order.asc("price") : Sort.Order.desc("price");
	        sort = sort != null ? sort.and(Sort.by(priceOrder)) : Sort.by(priceOrder);
	    }

	    // Add sorting criteria for 'rating' if 'ratingSorting' is not "nosort"
	    if (!"nosort".equalsIgnoreCase(ratingSorting)) {
	        Sort.Order ratingOrder = "asc".equalsIgnoreCase(ratingSorting) ? Sort.Order.asc("rating") : Sort.Order.desc("rating");
	        sort = sort != null ? sort.and(Sort.by(ratingOrder)) : Sort.by(ratingOrder);
	    }

	 // Fetch data using repository with Specification, Sort, and Pageable
	    Page<Course> coursePage = sort != null ?
	            courseRepository.findAll(spec, PageRequest.of(page, itemsPerPage, sort)) :
	            courseRepository.findAll(spec, pageable);

	    // Initialize the courses list
	    List<Course> courses = new ArrayList<>();

	    // Iterate through the fetched page of courses
	    coursePage.forEach(course -> {
	        // Ensure the 'user' associated with the course is fetched eagerly
	        ApplicationUser user = course.getUser(); // This will trigger lazy loading
	        user.getUsername(); // Triggering access to avoid lazy initialization outside the transaction

	        courses.add(course);
	    });
	    

	    return courses;
	}

	@Override
	public void rateCourse(RateCourseRequest request) {
		Rating rating = Rating.builder()
				.ratingId(new RatingId().setCourseId(request.getCourseId()).setUserId(request.getUserId()))
				.rating(request.getRating())
				.build();
		ratingRepository.save(rating);
		AtomicInteger totalRatingPoint = new AtomicInteger(0);
		AtomicInteger totalRatingCount = new AtomicInteger(0);
		ratingRepository.findByRatingIdCourseId(request.getCourseId())
			.stream()
			.forEach(r -> {
				totalRatingCount.getAndIncrement();
				totalRatingPoint.getAndAdd(r.getRating());
			});
		Course course = courseRepository.findById(request.getCourseId()).orElse(null);
		course.setRating(totalRatingPoint.get()/totalRatingCount.get());
		courseRepository.save(course);
	}

	@Override
	public CourseStatisticsResponse getCourseStatistics(int courseId) {
		Course course = courseRepository.findById(courseId).orElse(null);
		
		if (course == null) return null;
		
		return CourseStatisticsResponse.builder()
				.numberOfStudents(course.getEnrolledNumber())
				.averageRating(new BigDecimal(course.getRating()).setScale(2, RoundingMode.HALF_UP).floatValue())
				.estimatedRevenue(course.getEnrolledNumber() * course.getPrice())
				.ratingCount(ratingRepository.findByRatingIdCourseId(courseId).size())
				.build();
  }
	public Long countCourseWithSpecAndPaging(String authors, String difficulties, boolean priceRangeFilter,
			Float minPrice, Float maxPrice, boolean ratingRangeFilter, Float minRating, Float maxRating,
			Integer minEnrollment, Integer maxEnrollment, String nameSorting, String priceSorting, String ratingSorting,
			Integer itemsPerPage, Integer page) {
		// Create Specification based on filtering criteria
	    Specification<Course> spec = CourseSpecifications.filterCourses(authors, priceRangeFilter, minPrice, maxPrice,
	            ratingRangeFilter, minRating, maxRating, minEnrollment, maxEnrollment, difficulties);
	    return courseRepository.count(spec);
	}

	@Override
	public int getUserCourseRating(int userId, int courseId) {
		return ratingRepository.findById(new RatingId().setUserId(userId).setCourseId(courseId)).get().getRating();
	}

	@Override
	public UserProgress getUserCourseProgress(int userId, int courseId) {
		return userProgressRepository.findById(new UserProgressId().setCourseId(courseId).setUserId(userId)).orElse(null);
	}

	@Override
	public Course findCourseWithLessonsAndWithUserByCourseId(int courseId) {
		return courseRepository.findCourseWithLessonsAndWithUserByCourseId(courseId);
	}

	@Override
	public boolean buyCourse(BuyCourseRequest request) {
		Course course = courseRepository.findCourseWithLessonsAndWithUserByCourseId(request.getCourseId());
		ApplicationUser user = userRepository.findById(request.getUserId()).orElse(null);
		
		
		if (course == null || user == null || user.getBalance() < course.getPrice() || course.getUser().getUserId() == user.getUserId()) return false;
		
		UserProgress courseProgress = userProgressRepository.findById(new UserProgressId().setCourseId(request.getCourseId()).setUserId(request.getUserId())).orElse(null);
		
		if (courseProgress != null) return false;
		
		user.setBalance(user.getBalance() - course.getPrice());
		course.getUser().setBalance(course.getUser().getBalance() + course.getPrice());
		UserProgress progress = UserProgress.builder()
				.user(user)
				.course(course)
				.build();
		
		userRepository.save(user);
		userRepository.save(course.getUser());
		userProgressRepository.save(progress);
		return true;
	}

}
