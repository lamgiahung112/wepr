package hcmute.wepr.ielts_app.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('TEACHER')")
public @interface IsTeacher {

}
