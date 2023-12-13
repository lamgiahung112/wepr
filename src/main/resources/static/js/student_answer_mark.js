let currentAnswerId;
let url = window.location.href;
let segments = url.split('/');
let courseId = segments[segments.length - 2]; // The course ID should be the second-to-last segment

;
function updateUI() {
	$.ajax({
		url: 'http://localhost:8080/answer/course/'+courseId, // Replace with your API endpoint
		method: 'GET',
		dataType: 'json',
		success: function(data) {
			var studentAnswersList = $('#studentAnswersList');
			studentAnswersList.empty();

			data.forEach(function(answer) {
				var statusBadgeClass = (answer.studentAnswerStatus === 'PENDING') ? 'badge bg-warning' : 'badge bg-success';

				// Constructing a list item with concise details using Bootstrap badges
				var listItem = $('<a href="#" class="list-group-item list-group-item-action"></a>')
					.attr('href', 'http://localhost:8080/answer/' + answer.answerId)
					.append(
						$('<div class="d-flex justify-content-between align-items-center"></div>').append(
							$('<div></div>').append(
								$('<span class="badge bg-primary"></span>').text('Answer ID: ' + answer.answerId),
								$('<span class="badge bg-secondary ms-5"></span>').text('Created At: ' + new Date(answer.createAt).toLocaleString()),
								$('<span class="' + statusBadgeClass + ' ms-5"></span>').text('Status: ' + answer.studentAnswerStatus)
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
						currentAnswerId = data.answerId;

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
}

$(document).ready(function() {
	updateUI();

	$('#submitCorrectionButton').click(function(event) {
		event.preventDefault();

		console.log(currentAnswerId);
		var correctionText = $('#teacherCorrection').val();
		$.ajax({
			url: 'http://localhost:8080/correction/' + currentAnswerId,
			type: 'POST',
			data: {
				correctionText: correctionText
			},
			success: function(response) {
				// Handle success - for example, display a success message or perform further actions
				console.log(response);
				updateUI();
			},
			error: function(xhr, status, error) {
				// Handle error - display an error message or take appropriate action
				console.error(error);
			}
		});
	});
});
