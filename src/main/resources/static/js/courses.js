$(document).ready(function() {
	// Checkbox change event to toggle author filter
	$('#authorFilterSwitch').change(function() {
		if (this.checked) {
			$('#filter-by-author').addClass('show'); // Show the author filter
		} else {
			$('#filter-by-author').removeClass('show'); // Hide the author filter
		}
	});

	$('#rangeFilterSwitch').change(function() {
		if (this.checked) {
			$('#filter-by-range').addClass('show'); // Show the author filter
		} else {
			$('#filter-by-range').removeClass('show'); // Hide the author filter
		}
	});

	$('#difficultyFilterSwitch').change(function() {
		if (this.checked) {
			$('#filter-by-difficulty').addClass('show'); // Show the author filter
		} else {
			$('#filter-by-difficulty').removeClass('show'); // Hide the author filter
		}
	});

	$('#beginner-btn').click(function(event) {
		var button = $(this);

		// Check if the button has the 'btn-success' class
		if (button.hasClass('btn-success')) {
			button.removeClass('btn-success').addClass('btn-outline-success');
		} else {
			// If the button doesn't have 'btn-success', switch it to 'btn-success'
			button.removeClass('btn-outline-success').addClass('btn-success');
		}
	});
	
	$('#immediate-btn').click(function(event) {
		var button = $(this);
		if (button.hasClass('btn-warning')) {
			button.removeClass('btn-warning').addClass('btn-outline-warning');
		} else {
			button.removeClass('btn-outline-warning').addClass('btn-warning');
		}
	});
	
	$('#advanced-btn').click(function(event) {
		var button = $(this);
		if (button.hasClass('btn-danger')) {
			button.removeClass('btn-danger').addClass('btn-outline-danger');
		} else {
			button.removeClass('btn-outline-danger').addClass('btn-danger');
		}
	});

	// Add Author button click event
	$('#addAuthorBtn').click(function(event) {
		// Get the selected author value
		var selectedAuthor = $('#authorDropdown').val();
		// Get the text of the selected author option
		var selectedAuthorText = $('#authorDropdown option:selected').text();

		// Create a new author element with a close button
		var authorElement = '<div class="author-element m-2">' + selectedAuthorText +
			'<button class="btn btn-sm btn-danger closeAuthorBtn">x</button></div>';

		// Append the new author element to the authors container
		$('#authors-container').append(authorElement);
	});

	// Event delegation for dynamically added close buttons
	$('#authors-container').on('click', '.closeAuthorBtn', function() {
		$(this).parent().remove(); // Remove the corresponding author element
	});
});