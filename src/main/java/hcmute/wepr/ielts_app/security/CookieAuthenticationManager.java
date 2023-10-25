package hcmute.wepr.ielts_app.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import hcmute.wepr.ielts_app.Utilities.JwtDataWrapper;
import hcmute.wepr.ielts_app.Utilities.JwtUtils;

@Component
public class CookieAuthenticationManager implements AuthenticationManager {
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String authToken = authentication.getCredentials().toString();
		
		if (authToken == null || authToken.isEmpty() || !jwtUtils.validateToken(authToken)) {
			throw new InsufficientAuthenticationException("Invalid Credentials");
		}
		
		JwtDataWrapper jwtData = jwtUtils.parse(authToken);
		
		String role = jwtData.getRole();
		String username = jwtData.getUsername();
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return new UsernamePasswordAuthenticationToken(username, username, authorities);
	}

	
}
