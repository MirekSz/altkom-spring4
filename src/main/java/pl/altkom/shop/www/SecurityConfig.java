package pl.altkom.shop.www;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

import pl.altkom.shop.lib.Profiles;

@Configuration
@ImportResource("classpath:security.xml")
@Profile(Profiles.WEB)
public class SecurityConfig {
}
