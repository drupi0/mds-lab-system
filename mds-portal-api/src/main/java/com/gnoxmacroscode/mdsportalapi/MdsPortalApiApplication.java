package com.gnoxmacroscode.mdsportalapi;

import com.c4_soft.springaddons.security.oauth2.OAuthentication;
import com.c4_soft.springaddons.security.oauth2.OpenidClaimSet;
import com.c4_soft.springaddons.security.oauth2.config.synchronised.OAuth2AuthenticationFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@SpringBootApplication
public class MdsPortalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdsPortalApiApplication.class, args);
	}

	@Configuration
	@EnableMethodSecurity
	public static class SecurityConfig {
		@Bean
		OAuth2AuthenticationFactory authenticationFactory(Converter<Map<String, Object>, Collection<? extends GrantedAuthority>> authoritiesConverter) {
			return (bearerString, claims) -> new OAuthentication<>(new OpenidClaimSet(claims),
					authoritiesConverter.convert(claims), bearerString);
		}
	}

}
