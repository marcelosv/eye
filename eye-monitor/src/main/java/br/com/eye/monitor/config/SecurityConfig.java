package br.com.eye.monitor.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter { 

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		String[] permis = new String[]{
				"/receive"
		};

		// @formatter:off
		http
				.csrf().disable()
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers(
						HttpMethod.POST,
						permis
				).permitAll()

				.anyRequest().authenticated();

		// @formatter:on
	}
	
}
