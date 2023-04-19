package castleuk.selfsecurity.config;

import castleuk.selfsecurity.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

		private final PrincipalOauth2UserService principalOauth2UserService;

		@Bean
		public BCryptPasswordEncoder bCryptPasswordEncoder() {
				return new BCryptPasswordEncoder();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
				http.csrf().disable();
				http.authorizeRequests()
								.antMatchers("/user/**").authenticated()
								.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
								.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
								.anyRequest().permitAll()
								.and()
								.formLogin()
								.loginPage("/loginForm")
								.loginProcessingUrl("/login")
								.defaultSuccessUrl("/")
								.and()
								.oauth2Login()
								.loginPage("/loginForm")
								.userInfoEndpoint()
								.userService(principalOauth2UserService);
		}
}
