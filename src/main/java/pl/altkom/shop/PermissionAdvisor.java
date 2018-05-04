package pl.altkom.shop;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import pl.altkom.shop.UserProvider.MyUser;

@Component
public class PermissionAdvisor {
	public boolean can(Object root) {
		Authentication o = SecurityContextHolder.getContext().getAuthentication();
		MyUser principal = (MyUser) o.getPrincipal();
		return false;
	}
}
