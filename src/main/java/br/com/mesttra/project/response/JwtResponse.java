package br.com.mesttra.project.response;

import java.util.Collection;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String username;
	private Collection<String> roles;

	public JwtResponse(String accessToken, String username, Collection<String> roles) {
		this.token = accessToken;
		this.username = username;
		this.roles = roles;
	}

}
