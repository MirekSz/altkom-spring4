package pl.altkom.shop;

import java.util.Arrays;

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

		return new User(username, encode, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")// ,
																								// new
																								// SimpleGrantedAuthority("ROLE_ADMIN")
		));
	}

}
