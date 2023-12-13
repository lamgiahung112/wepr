$(document).ready(function() {
	$.ajax({
		url: 'http://localhost:8080/answer/course/253', // Replace with your API endpoint
		method: 'GET',
		dataType: 'json',
		success: function(data) {
			var studentAnswersList = $('#studentAnswersList');
			studentAnswersList.empty();

			// Loop through the retrieved data and populate the student answers list
			data.forEach(function(answer) {
				// Constructing a list item with concise details using Bootstrap badges
				var listItem = $('<a href="#" class="list-group-item list-group-item-action"></a>')
					.attr('href', 'http://localhost:8080/answer/' + answer.answerId)
					.append(
						$('<div class="d-flex justify-content-between align-items-center"></div>').append(
							$('<div></div>').append(
								$('<span class="badge bg-primary"></span>').text('Answer ID: ' + answer.answerId),
								$('<span class="badge bg-secondary ms-5"></span>').text('Created At: ' + new Date(answer.createAt).toLocaleString()),
								$('<span class="badge bg-info ms-5"></span>').text('Status: ' + answer.studentAnswerStatus)
								// Add more details as needed
							)
						)
					);
				studentAnswersList.append(listItem);
			});

			// Add click event listener to each list item for handling click events
			$('.list-group-item').on('click', function(e) {
				e.preventDefault();
				var href = $(this).attr('href');
				// Make an AJAX call to the href link to get data for the clicked answer
				$.ajax({
					url: href,
					method: 'GET',
					dataType: 'json',
					success: function(data) {
						// Populate data to specific HTML elements
						$('#writingExerciseText').text(data.writingExercise.title);
						$('#studentAnswerText').text('Answer: ' + data.answer);

						// If there is a writing correction response, populate the correction details
						if (data.writingCorrectionResponse) {
							$('#teacherCorrection').val(data.writingCorrectionResponse.correction);
						} else {
							$('#teacherCorrection').val(''); // Clear the correction textarea if there is no correction data
						}
					},
					error: function(xhr, status, error) {
						console.error(error);
					}
				});
			});
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
});
