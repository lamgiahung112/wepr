let lessonId;

$(document).ready(function() {
	let userId = $('#userIdHolder').data('user-id');
	let courseId = $('#courseIdHolder').data('course-id');
	
	$.ajax({
		type: 'GET',
		url: '/api/ratings/' + courseId,
		success: function(ratings) {
			if (ratings && ratings.length > 0) {
				// Clear existing ratings
				$('#allCourseRatings .rating-list').empty();

				// Loop through ratings and populate the HTML
				ratings.forEach(function(rating) {
					let stars = '★'.repeat(rating.rating) + '☆'.repeat(5 - rating.rating);
					let ratingHTML = `
                            <div class="rating-item">
                                <p>User ${rating.userId}</p>
                                <div class="user-rating">${stars}</div>
                                <p>${rating.comment}</p>
                            </div>
                        `;
					$('#allCourseRatings .rating-list').append(ratingHTML);
				});
			} else {
				// Display a message if there are no ratings
				$('#allCourseRatings .rating-list').html('<p>No ratings yet.</p>');
			}
		},
		error: function(xhr, status, error) {
			console.error('Error fetching ratings:', error);
			// Handle errors here, display an error message or perform necessary actions.
		}
	});
	// AJAX request to get the current user's progress
	$.ajax({
		type: 'GET',
		url: '/userprogress/getUserProgress',
		data: {
			userId: userId,
			courseId: courseId
		},
		success: function(userProgress) {
			// Update the UI to the user's progress
			if (userProgress && userProgress.lessonId) {
				var lessonId = userProgress.lessonId;
				var courseId = userProgress.courseId;
				$.ajax({
					type: 'GET',
					url: '/lessons/' + lessonId,
					success: function(data) {
						// Update the lesson video content
						console.log(data.video);
						$('#lessonVideo iframe').attr('src', data.video);
						$('#lessonTitleRight').text(data.title);
						$('#lessonDescriptionRight').text(data.description);
					},
					error: function(xhr, status, error) {
						console.error(error);
					}
				});
			}
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});

	$('.lesson-link').on('click', function(e) {
		e.preventDefault();
		lessonId = $(this).data('lesson-id');
		// AJAX request
		$.ajax({
			type: 'GET',
			url: '/lessons/' + lessonId,
			success: function(data) {
				// Update the lesson video content
				console.log(data.video);
				$('#lessonVideo iframe').attr('src', data.video);
				$('#lessonTitleRight').text(data.title);
				$('#lessonDescriptionRight').text(data.description);
			},
			error: function(xhr, status, error) {
				console.error(error);
			}
		});

		$.ajax({
			type: 'POST',
			url: '/userprogress/updateUserProgress',
			data: {
				userId: userId,
				courseId: courseId,
				lessonId: lessonId
			},
			success: function(userProgress) {
				console.log("update user progres successfully");
			},
			error: function(xhr, status, error) {
				console.error(error);
			}
		});
	});

	$('.star').click(function(event) {
		let starValue = $(this).data('value');
		$('#selectedRating').text(starValue);
	});
	
	$('#doExerciseButton').click(function(event) {
		window.location.href = "http://localhost:8080/exercise/" + lessonId;
	});

});

function submitRating() {
	let courseId = $('#courseIdHolder').data('course-id');
	let userId = $('#userIdHolder').data('user-id');
	let rating = parseInt($('#selectedRating').text());
	let comment = $('#reviewMessage').val();

	if (rating < 1 || rating > 5) {
		// Add validation for the rating (1 to 5 stars)
		console.log('Please select a rating between 1 and 5.');
		return;
	}

	let rateCourseRequest = {
		courseId: courseId,
		userId: userId,
		rating: rating,
		comment: comment
	};

	$.ajax({
		type: 'POST',
		url: '/api/ratings/rate-course',
		contentType: 'application/json',
		data: JSON.stringify(rateCourseRequest),
		success: function(response) {
			alert('Rating submitted successfully.');
			// You may want to perform additional actions here, such as updating UI or displaying a success message.
		},
		error: function(xhr, status, error) {
			console.error('Error submitting rating:', error);
			// Handle errors here, display an error message or perform necessary actions.
		}
	});

	$.ajax({
		type: 'GET',
		url: '/api/ratings/' + courseId,
		success: function(ratings) {
			if (ratings && ratings.length > 0) {
				// Clear existing ratings
				$('#allCourseRatings .rating-list').empty();

				// Loop through ratings and populate the HTML
				ratings.forEach(function(rating) {
					let stars = '★'.repeat(rating.rating) + '☆'.repeat(5 - rating.rating);
					let ratingHTML = `
                            <div class="rating-item">
                                <p>User ${rating.userId}</p>
                                <div class="user-rating">${stars}</div>
                                <p>${rating.comment}</p>
                            </div>
                        `;
					$('#allCourseRatings .rating-list').append(ratingHTML);
				});
			} else {
				// Display a message if there are no ratings
				$('#allCourseRatings .rating-list').html('<p>No ratings yet.</p>');
			}
		},
		error: function(xhr, status, error) {
			console.error('Error fetching ratings:', error);
			// Handle errors here, display an error message or perform necessary actions.
		}
	});
}