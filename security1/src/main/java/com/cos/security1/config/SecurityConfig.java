package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

		private final PrincipalOauth2UserService principalOauth2UserService;

//		//해당 메서드의 리턴되는 오브젝트를 IoC 등록
//		@Bean
//		public BCryptPasswordEncoder encodePwd() {
//				return new BCryptPasswordEncoder();
//		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
				http.csrf().disable();
				http.authorizeRequests()
								.antMatchers("/user/**").authenticated()
								.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
								.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
								.anyRequest().permitAll()
								.and()
								.formLogin()
								.loginPage("/loginForm")
								.loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행한다.
								.defaultSuccessUrl("/")
								.and()
								.oauth2Login()
								.loginPage("/loginForm")
								// 구글 로그인이 완료된 후처리가 필요함.  Tip. 코드x (엑세스토큰 + 사용자프로필정보)
								// 1. 코드 받기(인증), 2. 엑세스토큰(권한), 3. 사용자프로필 정보 가져옴, 4-1. 정보를 토대로 회원가입을 자동진행
								// 4-2. 추가 정보 필요시, 추가 정보 입력
								.userInfoEndpoint()
								.userService(principalOauth2UserService);
		}
}
