package hcmute.wepr.ielts_app.security;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CookieSecurityContextRepository implements SecurityContextRepository {
	private final Pattern[] STRICTLY_PUBLIC_ENDPOINT_WHITELIST = {
		Pattern.compile("^/auth/.*$"),
		Pattern.compile("^/webjars/.*$")
	};
	
	@Value("${auth.cookie.name}")
	private String COOKIE_NAME;

	@Autowired
	private CookieAuthenticationManager cookieAuthenticationManager;

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		return false;
	}

	private Optional<Cookie> readCookieFromRequest(HttpServletRequest request) {
		if (request.getCookies() == null) {
			return Optional.empty();
		}

		Optional<Cookie> maybeCookie = Stream.of(request.getCookies()).filter(c -> COOKIE_NAME.equals(c.getName()))
				.findFirst();

		return maybeCookie;
	}

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		HttpServletRequest request = requestResponseHolder.getRequest();
		HttpServletResponse response = requestResponseHolder.getResponse();
		
		for (Pattern endpoint : STRICTLY_PUBLIC_ENDPOINT_WHITELIST) {
			String currentEndpoint = request.getRequestURI();
			if (endpoint.matcher(currentEndpoint).matches()) {
				return new SecurityContextImpl();
			}
		}

		Optional<Cookie> cookie = readCookieFromRequest(request);
		
		if (cookie.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Invalid Credentials");
		}
		
		Authentication auth = cookieAuthenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(cookie.get().getValue(), cookie.get().getValue()));
		return new SecurityContextImpl(auth);
	}
}
