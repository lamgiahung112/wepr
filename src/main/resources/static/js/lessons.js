$(document).ready(function() {
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
    });
});