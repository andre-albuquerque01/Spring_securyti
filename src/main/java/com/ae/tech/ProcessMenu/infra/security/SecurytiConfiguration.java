package com.ae.tech.ProcessMenu.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurytiConfiguration {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/auth/register")
						.permitAll().requestMatchers(HttpMethod.POST, "/auth/recover").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/auth/users/{id}").hasRole("USER")
						.requestMatchers(HttpMethod.PATCH, "/auth/usersPass/{id}").hasRole("USER")
						.requestMatchers(HttpMethod.PUT, "/auth/updateUser/{id}").hasRole("USER")
//						Desnecessario
						.requestMatchers(HttpMethod.GET, "/auth/logout").hasRole("USER")

						.requestMatchers(HttpMethod.GET, "/product/").permitAll()
						.requestMatchers(HttpMethod.GET, "/product/searchProduct/{title}").permitAll()
						.requestMatchers(HttpMethod.GET, "/product/searchCategory/{category}").permitAll()
						.requestMatchers(HttpMethod.GET, "/product/findProduct/{id}").permitAll()
						.requestMatchers(HttpMethod.POST, "/product/register").hasRole("SUPERADM")
						.requestMatchers(HttpMethod.POST, "/product/register/image").hasRole("SUPERADM")
						.requestMatchers(HttpMethod.PUT, "/product/update/image/{id}").hasRole("SUPERADM")
						.requestMatchers(HttpMethod.PUT, "/product/update/{id}").hasRole("SUPERADM")
						.requestMatchers(HttpMethod.DELETE, "/product/del/{id}").hasRole("SUPERADM")
						.requestMatchers(HttpMethod.PATCH, "/product/updateStatus/{id}").hasRole("SUPERADM")
						.requestMatchers(HttpMethod.PATCH, "/product/like/{id}").hasRole("USER")
						.requestMatchers(HttpMethod.PATCH, "/product/update/qtd/{id}").hasRole("USER")

						.requestMatchers(HttpMethod.GET, "/order/orders").hasRole("FUNCIONARIO")
						.requestMatchers(HttpMethod.GET, "/order/{id}").hasRole("USER")
						.requestMatchers(HttpMethod.GET, "/order/userId/{id}").hasRole("USER")
						.requestMatchers(HttpMethod.GET, "/order/searchOrder/{number}").hasRole("USER")
						.requestMatchers(HttpMethod.POST, "/order/register").hasRole("USER")
						.requestMatchers(HttpMethod.PUT, "/order/update/{id}").hasRole("FUNCIONARIO")
						.requestMatchers(HttpMethod.PATCH, "/order/updateStatus/{id}").hasRole("FUNCIONARIO")
						.requestMatchers(HttpMethod.DELETE, "/order/del/{id}").hasRole("FUNCIONARIO").anyRequest()
						.authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
