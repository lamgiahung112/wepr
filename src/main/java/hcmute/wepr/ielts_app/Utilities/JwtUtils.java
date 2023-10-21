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
	
	public String getIdFromToken(String token) {
		return getAllClaimsFromToken(token).get("id", String.class);
	}
	
	public String getNameFromToken(String token) {
		return getAllClaimsFromToken(token).get("name", String.class);
	}

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).get("username", String.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }
	
    public String getRoleFromToken(String token) {
    	return getAllClaimsFromToken(token).get("roles", String.class);
    }
    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
	
	public String generateToken(Teacher teacher) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expTime);
		
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", teacher.getTeacherId());
		claims.put("username", teacher.getUsername());
		claims.put("name", teacher.getTeacherName());
		claims.put("role", "ROLE_TEACHER");
		
		return Jwts.builder()
				.setIssuedAt(now)
				.setExpiration(expiration)
				.addClaims(claims)
				.signWith(key)
				.compact();
	}
	
	public String generateToken(Student student) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expTime);
		
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", student.getId());
		claims.put("username", student.getUsername());
		claims.put("name", student.getStudentName());
		claims.put("role", "ROLE_STUDENT");
		
		return Jwts.builder()
				.setIssuedAt(now)
				.setExpiration(expiration)
				.addClaims(claims)
				.signWith(key)
				.compact();
	}
	
	public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
