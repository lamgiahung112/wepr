package hcmute.wepr.ielts_app.Utilities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtDataWrapper {
	private String id;
	private String email;
	private String username;
	private String role;
}
