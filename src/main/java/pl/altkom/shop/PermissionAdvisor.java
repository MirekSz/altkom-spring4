package pl.altkom.shop;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionAdvisor {
	public boolean can(Object root) {
		Authentication o = SecurityContextHolder.getContext().getAuthentication();
		return false;
	}
}
