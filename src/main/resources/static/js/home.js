function fetchCourses(page = 1) {
	let filterData = '';
	let sortData = '';
	let paginationData = '';


	// Pagination section
	const itemsPerPage = parseInt('6');
	paginationData += `itemsPerPage=${itemsPerPage}`;

	const baseURL = 'http://localhost:8080/courses/find'; // Replace with your actual API endpoint
	const finalURL = `${baseURL}?${filterData}${sortData}${paginationData}&page=${page-1}`;
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
                <div class="col-md-4 mb-4">
                	<div class="card">
                    <img src="https://via.placeholder.com/200x200" class="card-img-top" alt="Course Image">
                    <div class="card-body">
                        <p class="card-text">Author: <a href="#">${course.author}</a></p>
                            <h5 class="card-title">${course.courseName}</h5>
                            <div class="d-flex justify-content-between">
                            <p class="card-text">Price: $${course.price}</p>
                            <button class="btn btn-primary">Buy Now</button> 
							</div>
                            <p class="card-text">Rating: ${course.rating}</p>
                            <p class="card-text">Enrolled: ${course.enrolledNumber}</p>
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
						fetchCourses(currentPage);
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
	fetchCourses();
});