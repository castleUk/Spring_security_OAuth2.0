package castleuk.selfsecurity.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	//  구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수

		@Override
		public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
				System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); // registrationId로 어떤 Oauth로 로그인 했는지 확인가능.
				System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());

				OAuth2User oAuth2User = super.loadUser(userRequest);
				//구글 로그인 버튼 클릭 -> 로그인창 -> 로그인 완료 -> Code를 리턴(OAuth-client라이브러리) -> AccessToken 요청
				//  userRequest 정보 -> loadUser 함수 호출  -> 구글로부터 회원프로필 받아줌
				System.out.println("getAttributes :" + oAuth2User.getAuthorities());

				//회원가입을 강제로 진행
				return super.loadUser(userRequest);
		}
}
