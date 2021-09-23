package br.com.mesttra.project.security.service;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mesttra.project.entity.Role;
import br.com.mesttra.project.entity.User;
import br.com.mesttra.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(format("Username %s not found", username)));

		log.info("Username {} found.", username);
		Collection<Role> roles = user.getRoles();
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName().name()));
		});

		return new UserDetailsImpl(user, authorities);
	}

}
