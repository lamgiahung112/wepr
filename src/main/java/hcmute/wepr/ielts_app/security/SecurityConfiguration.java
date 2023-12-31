package hcmute.wepr.ielts_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private SecurityContextRepository securityContextRepository;

	@Bean
	public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
		return http
				.securityContext(t -> t.securityContextRepository(securityContextRepository))
				.authorizeHttpRequests(
						customizer -> customizer
							.requestMatchers("/auth/**").permitAll()
							.requestMatchers("/uploads/**").permitAll()
							.requestMatchers("/dashboard").permitAll()
							.requestMatchers("/courses").permitAll()
							.requestMatchers("/home").permitAll()
							.requestMatchers("/home/**").permitAll()
							.requestMatchers("/js/**").permitAll()
							.requestMatchers("/css/**").permitAll()
							.requestMatchers("/images/**").permitAll()
							.requestMatchers("/webjars/**").permitAll()
							.requestMatchers("/admin/login").permitAll()
							.anyRequest().authenticated()
				)
				.formLogin(customizer -> customizer.disable())
				.csrf(customizer -> customizer.disable())
				.exceptionHandling(customizer -> {
					customizer.accessDeniedPage("/auth/student/login");
				})
				.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
