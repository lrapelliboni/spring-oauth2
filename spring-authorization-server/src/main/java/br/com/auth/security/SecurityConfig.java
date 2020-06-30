package br.com.auth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
		resources.resourceId("spring-authorization-server");
	}

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/").permitAll()
				.anyRequest().authenticated();

		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();

//    	http
//    	.sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and().httpBasic()
//        .and().csrf().disable();
    }
}