let url = window.location.href;
let lessonId = url.substring(url.lastIndexOf('/') + 1);
function updateUI() {


	$.ajax({
		url: '/exercise/answerAttempts/' + lessonId,
		type: 'GET',
		success: function(response) {
			let previousAttemptsList = $('#previousAttemptsList');
			previousAttemptsList.empty();

			if (response.length === 0) {
				previousAttemptsList.append('<div class="d-flex flex-column"><p class="text-muted">No attempts yet.</p></div>');
			} else {
				response.forEach(function(attempt) {
					let submissionDate = new Date(attempt.createAt).toLocaleDateString();

					let listItem = '<div id="attempInfo">' +
						'<h5>Attempt ID: ' + attempt.answerId + '</h5>' +
						'<h5>Submitted At: ' + submissionDate + '</h5>' +
						'</div>' +
						'<div id="answerSection" class="d-flex flex-column justify-content-around">' +
						'<div class="w-100 d-flex flex-row" id="title">' +
						'<div class="w-100 d-flex flex-row justify-content-center">' +
						'<h5>Student answer</h5>' +
						'</div>' +
						'<div class="w-100 d-flex flex-row justify-content-center">' +
						'<h5>Teacher response</h5>' +
						'</div>' +
						'</div>' +
						'<div class="w-100 d-flex flex-row" id="title">' +
						'<div class="w-50 d-flex flex-column align-items-start" id="studentAnswer">' +
						'<div class="w-100">' +
						'<p>' + attempt.answer + '</p>' +
						'</div>' +
						'</div>' +
						'<div class="w-50 d-flex flex-column align-items-start" id="teacherResponse">' +
						'<div class="w-100">' +
						'<p>' + (attempt.writingCorrectionResponse ? attempt.writingCorrectionResponse.correction : '(PENDING...) Please wait for teacher to response') + '</p>' +
						'</div>' +
						'</div>' +
						'</div>' +
						'</div>';

					previousAttemptsList.append(listItem);
				});
			}
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}

// Function to submit the exercise answer
function submitExerciseAnswer() {
	// Get the exercise answer from the textarea or input field
	let exerciseAnswer = $('#exerciseAnswer').val();
	if (exerciseAnswer == "") {
		alert("The answer is blank!");
		return;
	}
	// Ajax call to submit the exercise answer
	$.ajax({
		type: 'POST', // Change this to 'POST' if your endpoint expects a POST request
		url: '/exercise/submit/' + lessonId,
		data: {
			answer: exerciseAnswer
		},
		success: function(response) {
			updateUI();
			alert("Submit exercise successfully!");
		},
		error: function(xhr, status, error) {
			// Handle error response
			console.error('Error submitting exercise:', error);
			// Optionally, you can display an error message or perform other actions
		}
	});
}

$(document).ready(function() {
	updateUI();
	$('#submitExerciseButton').prop("disabled", true);

	let timer = 3600; // Starting time in seconds (1 hour)
	let interval;

	function startTimer() {
		interval = setInterval(function() {
			if (timer > 0) {
				timer--;
				const hours = Math.floor(timer / 3600);
				const minutes = Math.floor((timer % 3600) / 60);
				const seconds = timer % 60;
				const formattedTime = `${padZero(hours)}:${padZero(minutes)}:${padZero(seconds)}`;
				document.getElementById('timer').innerText = formattedTime;
			} else {
				clearInterval(interval);
			}
		}, 1000);
	}

	// Function to pad zero for single digits
	function padZero(value) {
		return value < 10 ? `0${value}` : value;
	}

	// Event listener for the start button
	$('#startTimerBtn').click(function() {
		$('#submitExerciseButton').prop('disabled', false);
		//startTimer();
	});

	$('#submitExerciseButton').click(function() {
		submitExerciseAnswer();
		
		$('#exerciseAnswer').val("");
	});
});