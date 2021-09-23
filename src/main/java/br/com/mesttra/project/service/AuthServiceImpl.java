package br.com.mesttra.project.service;

import static br.com.mesttra.project.enums.ERole.ADMIN;
import static br.com.mesttra.project.enums.ERole.BUYER;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mesttra.project.entity.Role;
import br.com.mesttra.project.entity.User;
import br.com.mesttra.project.repository.RoleRepository;
import br.com.mesttra.project.repository.UserRepository;
import br.com.mesttra.project.request.LoginRequest;
import br.com.mesttra.project.request.SignupRequest;
import br.com.mesttra.project.response.JwtResponse;
import br.com.mesttra.project.response.MessageResponse;
import br.com.mesttra.project.security.service.UserDetailsImpl;
import br.com.mesttra.project.security.util.JwtUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {
	
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder encoder;
	private JwtUtil JwtUtil;

	@Override
	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = JwtUtil.generateToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(toList());
		
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getUsername(), roles));
	}

	@Override
	public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername()))
			return ResponseEntity.badRequest().body(new MessageResponse(format("User %s is already taken!", signUpRequest.getUsername())));

		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

		Collection<String> strRoles = signUpRequest.getRole();
		Collection<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			if (!hasText(role))
				role = "BUYER";

			switch (role.toUpperCase()) {
			case "ADMIN":
				roles.add(roleRepository.findByName(ADMIN));
				break;
			case "BUYER":
				roles.add(roleRepository.findByName(BUYER));
				break;
			default:
				throw new RuntimeException(format("Error: Role %s not found.", role));
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse(format("User %s registered successfully!", user.getUsername())));

	}
}
