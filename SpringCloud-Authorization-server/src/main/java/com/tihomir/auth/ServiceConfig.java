package com.tihomir.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * Simple and Dummy User Store.
 * @author Tihomir Raychev
 *
 */
@Configuration
public class ServiceConfig extends GlobalAuthenticationConfigurerAdapter {

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("tihomir").password("password").roles("USER").and().withUser("admin")
				.password("admin").roles("USER", "OPERATOR");
	}
}
