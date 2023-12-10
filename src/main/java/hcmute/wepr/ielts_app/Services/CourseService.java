package hcmute.wepr.ielts_app.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import hcmute.wepr.ielts_app.Models.Course;
import hcmute.wepr.ielts_app.Models.Lesson;
import hcmute.wepr.ielts_app.Models.enums.DifficultLevel;
import hcmute.wepr.ielts_app.Services.Interfaces.CourseServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.CreateNewCourseRequest;
import hcmute.wepr.ielts_app.Utilities.Requests.UpdateCourseRequest;
import hcmute.wepr.ielts_app.repositories.CourseRepositoryInterface;
import hcmute.wepr.ielts_app.repositories.LessonRepositoryInterface;
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
	public List<Course> getCourseWithSpecAndPaging(String authors, String difficulties, float minPrice, float maxPrice,
			float minRating, float maxRating, Integer minEnrollment, Integer maxEnrollment, String nameSorting,
			String priceSorting, String ratingSorting, Integer itemsPerPage, Integer page) {
		// Create Sort object based on sorting parameters
		Sort sort = Sort.by(nameSorting.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "courseName")
				.and(Sort.by(priceSorting.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"))
				.and(Sort.by(ratingSorting.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "rating"));
		
		// Create Pageable object for pagination
		Pageable pageable = PageRequest.of(page, itemsPerPage, sort);
		
		// Create Specification based on filtering criteria
        Specification<Course> spec = CourseSpecifications.filterCourses(authors, minPrice, maxPrice,
                minRating, maxRating, minEnrollment, maxEnrollment, difficulties);

        // Fetch data using repository with Specification and Pageable
        return courseRepository.findAll(spec, pageable).toList();
	}

}
