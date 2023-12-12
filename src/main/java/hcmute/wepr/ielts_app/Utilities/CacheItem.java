package hcmute.wepr.ielts_app.Utilities;

import hcmute.wepr.ielts_app.Models.ApplicationUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CacheItem {
	private ApplicationUser user;
	private long expirationTime;
}
