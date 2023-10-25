package hcmute.wepr.ielts_app.Utilities;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import hcmute.wepr.ielts_app.Models.Student;
import hcmute.wepr.ielts_app.Models.Teacher;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtils {
	@Value("${auth.jwt.secret}")
	private String secret;
	
	@Value("${auth.jwt.exp_time}")
	private long expTime;
	
	private Key key;
	
	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }
    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
	
	public String generateToken(JwtDataWrapper data) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expTime);
		
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", data.getId());
		claims.put("username", data.getUsername());
		claims.put("name", data.getName());
		claims.put("role", data.getRole());
		
		return Jwts.builder()
				.setIssuedAt(now)
				.setExpiration(expiration)
				.addClaims(claims)
				.signWith(key)
				.compact();
	}
	
	public JwtDataWrapper parse(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return JwtDataWrapper.builder()
				.id(claims.get("id", String.class))
				.name(claims.get("name", String.class))
				.username(claims.get("username", String.class))
				.role(claims.get("role", String.class))
				.build();
	}
	
	public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
