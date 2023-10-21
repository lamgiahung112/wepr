package hcmute.wepr.ielts_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
	@Autowired
	private AuthenticationManager cookieAuthManager;

	@Autowired
	private SecurityContextRepository securityContextRepository;

	@Bean
	public DefaultSecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
		return http.authenticationManager(cookieAuthManager)
				.securityContext(t -> t.securityContextRepository(securityContextRepository))
				.authorizeHttpRequests(customizer -> {
					customizer
						.requestMatchers("/login").permitAll()
						.requestMatchers("/uploads/**").permitAll()
						.requestMatchers("/dashboard").permitAll()
						.anyRequest().authenticated();
				})

				.formLogin(customizer -> {
					customizer.disable();
				}).build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
