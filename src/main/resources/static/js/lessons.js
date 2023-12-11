$(document).ready(function() {
	let userId = $('#userIdHolder').data('user-id');
	let courseId = $('#courseIdHolder').data('course-id');
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
		var lessonId = $(this).data('lesson-id');
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
});