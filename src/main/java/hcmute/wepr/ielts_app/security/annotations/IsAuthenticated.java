package hcmute.wepr.ielts_app.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT') or hasRole('ADMIN')")
public @interface IsAuthenticated {

}
