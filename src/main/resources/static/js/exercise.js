$(document).ready(function() {

	let timer = 3600; // Starting time in seconds (1 hour)
	let interval;

	// Function to update timer after every second
	function startTimer() {
		interval = setInterval(function() {
			if (timer > 0) {
				timer--;
				const hours = Math.floor(timer / 3600);
				const minutes = Math.floor((timer % 3600) / 60);
				const seconds = timer % 60;
				const formattedTime = `${padZero(hours)}:${padZero(minutes)}:${padZero(seconds)}`;
				$('#timer').text(formattedTime);
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
		startTimer();
	});
});