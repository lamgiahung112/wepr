function updateCourses(page = 1) {
	let filterData = '';
	let sortData = '';
	let paginationData = '';

	let teacherUsername = $('#username-holder').text();

	filterData += `authors=${teacherUsername}&`; // Construct authors filter parameter

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

	const baseURL = 'http://localhost:8080/courses/find'; // Replace with your actual API endpoint
	const finalURL = `${baseURL}?${filterData}${sortData}${paginationData}&page=${page - 1}`;
	console.log(finalURL);

	// Make an AJAX POST request to the URL
	$.ajax({
		type: 'GET',
		url: finalURL,
		success: function(response) {
			// Handle the successful response
			console.log('Success!', response);

			// Function to generate the HTML structure for a single course
			function createCourseCard(course) {
				let difficultyClass = '';
				switch (course.difficulty) {
					case 'BEGINNER':
						difficultyClass = 'badge bg-success';
						break;
					case 'INTERMEDIATE':
						difficultyClass = 'badge bg-warning text-dark';
						break;
					case 'ADVANCED':
						difficultyClass = 'badge bg-danger';
						break;
					default:
						difficultyClass = 'badge bg-primary';
						break;
				}
				return `
                <div class="col-md-3 mb-4">
                    <div class="card course-card">
                        <div class="course-img-section">
                            <img src="https://via.placeholder.com/200x200" class="card-img-top course-img" alt="Course Image">
                            
                        </div>
                        <div class="card-body" style="background-color: #f8d7da;">
                        	<p class="card-text">Author: <a href="#">${course.author}</a></p>
                            <h5 class="card-title">${course.courseName}</h5>
                            <p class="card-text">Price: $${course.price}</p>
                            <p class="card-text">Rating: ${course.rating}</p>
                            <p class="card-text">Enrolled: ${course.enrolledNumber}</p>
                			<span class="${difficultyClass}">${course.difficulty}</span>
                            <div class="collapse" id="detailsCollapse${course.courseId}">
                                <p>Additional details here...</p>
                                <button class="btn btn-primary btn-details">Update</button>
                            </div>
                        </div>
                    </div>
                </div>
            `;
			}

			// Function to render the courses onto the courses-container
			function renderCourses(courses) {
				var coursesContainer = $('#courses-container'); // Get the courses container element
				coursesContainer.empty();

				// Iterate through the courses and create HTML for each course
				courses.forEach(function(course) {
					var courseHTML = createCourseCard(course); // Generate HTML for a single course
					coursesContainer.append(courseHTML); // Append the course HTML to the container
				});
			}

			// Call the renderCourses function with the fetched course data
			renderCourses(response.courses); // Assuming response contains an array of course objects

			const container = $("#paginationButtonContainer");
			container.empty(); // Clear previous buttons
			let totalNumber = response.totalNumber;
			let currentPage = page;
			const totalPages = Math.ceil(totalNumber / itemsPerPage);

			if (totalPages <= 1) {
				return; // No need for pagination if there's only one page
			}

			const pagination = $("<ul>").addClass("pagination");

			// Function to create pagination button
			function createPaginationButton(label, page) {
				const button = $("<li>").addClass("page-item");
				const link = $("<a>")
					.addClass("page-link")
					.attr("href", "#")
					.text(label)
					.on("click", function() {
						currentPage = page; // Update current page when a button is clicked
						renderPagination(); // Re-render pagination buttons
						updateCoursesCourses(currentPage);
					});

				if (page === currentPage) {
					button.addClass("active"); // Add 'active' class to the current page button
				}

				button.append(link);
				return button;
			}

			const maxVisibleButtons = 5; // Maximum number of visible pagination buttons

			// Function to render pagination buttons
			function renderPagination() {
				pagination.empty(); // Clear previous pagination buttons

				// Add Previous button
				if (currentPage > 1) {
					pagination.append(createPaginationButton("Previous", currentPage - 1));
				}

				// Calculate starting and ending page numbers for pagination display
				let startPage = Math.max(1, currentPage - Math.floor(maxVisibleButtons / 2));
				let endPage = Math.min(totalPages, startPage + maxVisibleButtons - 1);

				if (endPage - startPage + 1 < maxVisibleButtons) {
					startPage = Math.max(1, endPage - maxVisibleButtons + 1);
				}

				// Add numeric page buttons within the range
				for (let i = startPage; i <= endPage; i++) {
					pagination.append(createPaginationButton(i, i));
				}

				// Add Next button
				if (currentPage < totalPages) {
					pagination.append(createPaginationButton("Next", currentPage + 1));
				}

				container.append(pagination);
			}

			// Initial rendering of pagination buttons
			renderPagination();
		},
		error: function(xhr, status, error) {
			// Handle errors
			console.error('Error:', status, error);
		}
	});


}
$(document).ready(function() {

	updateCourses();

	$.ajax({
		url: 'http://localhost:8080/courses/getTeacherName',
		type: 'GET',
		dataType: 'json',
		success: function(data) {
			// On success, iterate through the received data and populate the dropdown
			if (data && data.length > 0) {
				var $authorDropdown = $('#authorDropdown');

				// Clear previous options in the dropdown
				$authorDropdown.empty();

				// Loop through the data and append options to the dropdown
				$.each(data, function(index, author) {
					$authorDropdown.append($('<option>', {
						value: author.username,
						text: author.name
					}));
				});
			} else {
				// Handle empty data or error condition
				console.log('No data received');
			}
		},
		error: function(xhr, status, error) {
			// Handle errors
			console.error('Error:', status, error);
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

	$('#intermediate-btn').click(function(event) {
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

	$('#get-result-btn').click( () => {
		updateCourses(0);
	});

});

// Use event delegation with .on() for hover on .course-card elements
$(document).on('mouseenter', '.course-card', function() {
	$(this).find('.collapse').collapse('show');
}).on('mouseleave', '.course-card', function() {
	$(this).find('.collapse').collapse('hide');
});
