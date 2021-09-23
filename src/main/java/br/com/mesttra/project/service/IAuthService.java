package br.com.mesttra.project.service;

import org.springframework.http.ResponseEntity;

import br.com.mesttra.project.request.LoginRequest;
import br.com.mesttra.project.request.SignupRequest;

public interface IAuthService {
	
	ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

	ResponseEntity<?> registerUser(SignupRequest signUpRequest);

}
