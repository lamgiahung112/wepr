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

	$('.sort-mode-btn').click(function(event) {
		$(this).toggleClass('rotate180');
		$(this).toggleClass('rotate0');
	});

	$('#increaseButton').click(function() {
		let value = parseInt($('#paginationLabel').text());
		value++;
		$('#paginationLabel').text(value);
	});

	$('#decreaseButton').click(function() {
		let value = parseInt($('#paginationLabel').text());
		if (value > 1) {
			value--;
			$('#paginationLabel').text(value);
		}
	});

	$('#get-result-btn').click(function(event) {
		let filterData = '';
		let sortData = '';
		let paginationData = '';

		// Filter section
		if ($('#authorFilterSwitch').is(':checked')) {
			// Scan through authors container to get selected authors
			const selectedAuthors = [];
			$('#authors-container .author-element').each(function() {
				const authorName = $(this).contents().filter(function() {
					return this.nodeType === 3; // Select only text nodes
				}).text().trim().replace(/ /g, '%20'); // Replace spaces with %20
				selectedAuthors.push(authorName); // Collect selected authors
			});

			if (selectedAuthors.length > 0) {
				filterData += `authors=${selectedAuthors.join(',')}&`; // Construct authors filter parameter
			}
		}

		// Filter section for ranges
		if ($('#rangeFilterSwitch').is(':checked')) {
			if ($('#priceSwitch').is(':checked')) {
				const minPrice = parseInt($('#customRangeMin').val());
				const maxPrice = parseInt($('#customRangeMax').val());

				if (!isNaN(minPrice) && !isNaN(maxPrice) && minPrice <= maxPrice) {
					filterData += `minPrice=${minPrice}&maxPrice=${maxPrice}&`;
				}
			}

			if ($('#ratingSwitch').is(':checked')) {
				const minRating = parseInt($('#customRatingMin').val());
				const maxRating = parseInt($('#customRatingMax').val());

				if (!isNaN(minRating) && !isNaN(maxRating) && minRating <= maxRating) {
					filterData += `minRating=${minRating}&maxRating=${maxRating}&`;
				}
			}

			if ($('#enrollmentSwitch').is(':checked')) {
				const minEnrollment = parseInt($('#customEnrollmentMin').val());
				const maxEnrollment = parseInt($('#customEnrollmentMax').val());

				if (!isNaN(minEnrollment) && !isNaN(maxEnrollment) && minEnrollment <= maxEnrollment) {
					filterData += `minEnrollment=${minEnrollment}&maxEnrollment=${maxEnrollment}&`;
				}
			}
		}

		if ($('#difficultyFilterSwitch').is(':checked')) {
			let selectedDifficulties = [];
			$('#filter-by-difficulty button').each(function() {
				const buttonClasses = $(this).attr('class'); // Get the class attribute of the button
				const buttonText = $(this).text().toUpperCase();

				// Check if the class attribute contains 'outline'
				if (buttonClasses.indexOf('outline') === -1) {
					selectedDifficulties.push(buttonText); // Add selected difficulty to the array
				}
			});
			if (selectedDifficulties.length > 0) {
				filterData += `difficulties=${selectedDifficulties.join(',')}&`; // Construct difficulty filter parameter
			}
		}

		if ($('#nameSortSwitch').is(':checked')) {
			const nameSortMode = $('#sort-mode-btn1').hasClass('rotate0') ? 'asc' : 'desc';
			sortData += `name=${nameSortMode}&`;
		}

		if ($('#priceSortSwitch').is(':checked')) {
			const priceSortMode = $('#sort-mode-btn2').hasClass('rotate0') ? 'asc' : 'desc';
			sortData += `price=${priceSortMode}&`;
		}

		if ($('#ratingSortSwitch').is(':checked')) {
			const ratingSortMode = $('#sort-mode-btn3').hasClass('rotate0') ? 'asc' : 'desc';
			sortData += `rating=${ratingSortMode}&`;
		}

		// Pagination section
		const itemsPerPage = parseInt($('#paginationLabel').text());
		paginationData += `itemsPerPage=${itemsPerPage}`;

		const baseURL = 'http://localhost:8080/courses'; // Replace with your actual API endpoint
		const finalURL = `${baseURL}?${filterData}${sortData}${paginationData}`;
		console.log(finalURL);

		// Make an AJAX POST request to the URL
		$.ajax({
			type: 'POST',
			url: finalURL,
			success: function(response) {
				// Handle the successful response
				console.log('Success!', response);
				// Do something with the response data
			},
			error: function(xhr, status, error) {
				// Handle errors
				console.error('Error:', status, error);
			}
		});
	});
});