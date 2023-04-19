package castleuk.selfsecurity.auth;
import castleuk.selfsecurity.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료되면, 시큐리티가 session을 만든다.(Security COntextHolder)
// 이 세션안에는 Authentication 타입의 객체만 담길수 있다.
// Authentication 안에는 User 정보가 있어야하고, 그 유저 정보는 UserDetails 타입으로 존재한다.
// 우리는 여기서 UserDetails를 implements 받은 PrincipalDetails를 만들어서 그걸 넣어 줄거임.

@Data
public class PrincipalDetails implements UserDetails {

 private User user;

		public PrincipalDetails(User user) {
				this.user = user;
		}

		//해당 User의 권한을 리턴하는 곳!!
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
				Collection<GrantedAuthority> collection = new ArrayList<>();
				collection.add(new GrantedAuthority() {
						@Override
						public String getAuthority() {
								return user.getRole();
						}
				});
				return collection;
		}

		@Override
		public String getPassword() {
				return user.getPassword();
		}

		@Override
		public String getUsername() {
				return user.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
				return true;
		}

		@Override
		public boolean isAccountNonLocked() {
				return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
				return true;
		}

		@Override
		public boolean isEnabled() {
				return true;
		}
}
