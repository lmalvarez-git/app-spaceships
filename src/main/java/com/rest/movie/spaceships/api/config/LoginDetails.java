package com.rest.movie.spaceships.api.config;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rest.movie.spaceships.api.model.Userlogin;
import com.rest.movie.spaceships.api.repository.UserRepository;

@Service
public class LoginDetails implements AuthenticationProvider {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	LoginDetails(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}
	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final  Optional<Userlogin> users = repository.findByUserName(authentication.getName()).stream().findFirst();
		
		if (users.isEmpty()) {
			 throw new BadCredentialsException("No user registered with this details!");
		} else  {
			final Userlogin userLogin = users.get();
			if (passwordEncoder.matches(authentication.getCredentials().toString(), userLogin.getPassword())) {
				final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userLogin.getRole()));      
				return new UsernamePasswordAuthenticationToken(userLogin.getUserName(), authentication.getCredentials().toString(), authorities);
			} else {
				throw new BadCredentialsException("Invalid password!");
			}
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		  return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
