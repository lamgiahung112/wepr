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
	
	$('#priceSwitch').change(function() {
		if (this.checked) {
			$('#price-select-container').addClass('show'); // Show the author filter
		} else {
			$('#price-select-container').removeClass('show'); // Hide the author filter
		}
	});
	
	$('#ratingSwitch').change(function() {
		if (this.checked) {
			$('#rating-select-container').addClass('show'); // Show the author filter
		} else {
			$('#rating-select-container').removeClass('show'); // Hide the author filter
		}
	});
	
	$('#enrollmentSwitch').change(function() {
		if (this.checked) {
			$('#enrollment-select-container').addClass('show'); // Show the author filter
		} else {
			$('#enrollment-select-container').removeClass('show'); // Hide the author filter
		}
	});
	
	function updateRangeNotify() {
        var minPrice = parseInt($('#customRangeMin').val());
        var maxPrice = parseInt($('#customRangeMax').val());
        var notifyMessage = "";

        if (maxPrice < minPrice) {
            notifyMessage = "Invalid price range"; // Show message for invalid range
        } else {
            notifyMessage = "Price from " + minPrice + " VND " + " to " + maxPrice + " VND";
        }

        $('#price-range-notify p').text(notifyMessage);
    }

    // Initial update on load
    updateRangeNotify();

    // Event listeners for range slider change
    $('#customRangeMin, #customRangeMax').on('input', function() {
        updateRangeNotify();
    });

	$('#difficultyFilterSwitch').change(function() {
		if (this.checked) {
			$('#filter-by-difficulty').addClass('show'); // Show the author filter
		} else {
			$('#filter-by-difficulty').removeClass('show'); // Hide the author filter
		}
	});
	
	function updateRatingNotify() {
        var minRating = parseInt($('#customRatingMin').val());
        var maxRating = parseInt($('#customRatingMax').val());
        var notifyMessage = "";

        if (maxRating < minRating) {
            notifyMessage = "Invalid rating range"; // Show message for invalid range
        } else {
            notifyMessage = "Rating from " + minRating + " star " + " to " + maxRating + " stars";
        }

        $('#rating-range-notify p').text(notifyMessage);
    }

    // Initial update on load
    updateRatingNotify();

    // Event listeners for range slider change
    $('#customRatingMin, #customRatingMax').on('input', function() {
        updateRatingNotify();
    });
    
    function updateEnrollmentNotify() {
        var minEnrollment = parseInt($('#customEnrollmentMin').val());
        var maxEnrollment = parseInt($('#customEnrollmentMax').val());
        var notifyMessage = "";

        if (maxEnrollment < minEnrollment) {
            notifyMessage = "Invalid enrollment range"; // Show message for invalid range
        } else {
            notifyMessage = "From " + minEnrollment + " to " + maxEnrollment + " people enrolled";
        }

        $('#enrollment-range-notify p').text(notifyMessage);
    }

    // Initial update on load
    updateEnrollmentNotify();

    // Event listeners for range slider change
    $('#customEnrollmentMin, #customEnrollmentMax').on('input', function() {
        updateEnrollmentNotify();
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