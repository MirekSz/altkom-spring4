package pl.altkom.shop;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserProvider implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String encode = new BCryptPasswordEncoder().encode(username);

		return new MyUser(username, encode, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")// ,
																									// new
																									// SimpleGrantedAuthority("ROLE_ADMIN")
		));
	}

	public static class MyUser extends User {

		public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
			super(username, password, authorities);
		}

	}

}
