package castleuk.selfsecurity.controller;

import castleuk.selfsecurity.auth.PrincipalDetails;
import castleuk.selfsecurity.model.User;
import castleuk.selfsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

		private final UserRepository userRepository;
		private final BCryptPasswordEncoder bCryptPasswordEncoder;


		@GetMapping("/test/login")
		public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
				System.out.println("/test/login=======================================================");
				PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
				System.out.println("authentication : " + principalDetails.getUser());
				System.out.println("userDetails : "+ userDetails.getUser());

				return "세션 정보 확인하기";
		}

		@GetMapping("/test/oauth/login")
		public @ResponseBody String oauthLoginTest(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth) {
				System.out.println("/test/login ===============");
				OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
				System.out.println("authentication : " + oAuth2User.getAttributes());

				return "OAuth2 세션 정보 확인하기";
		}


		@GetMapping({"", "/"})
		public String index() {
				return "index";
		}


		@GetMapping("/user")
		public @ResponseBody String user() {
				return "user";
		}

		@GetMapping("/manager")
		public @ResponseBody String manager() {
				return "manager";
		}

		@GetMapping("/admin")
		public @ResponseBody String admin() {
				return "admin";
		}

		//스프링 시큐리티가 해당주소를 낚아 채버리넹..
		@GetMapping("/loginForm")
		public String loginForm() {
				return "loginForm";
		}

		@GetMapping("/joinForm")
		public String joinForm() {
				return "joinForm";
		}

		@PostMapping("/join")
		public String join(User user) {
				System.out.println(user);
				user.setRole("ROLE_USER");
				String rawPassword = user.getPassword();
				String encPassword = bCryptPasswordEncoder.encode(rawPassword);
				user.setPassword(encPassword);
				userRepository.save(user);
				return "redirect:/loginForm";
		}

}