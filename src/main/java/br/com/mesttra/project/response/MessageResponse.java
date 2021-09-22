package br.com.mesttra.project.response;

import lombok.Getter;

@Getter
public class MessageResponse {

	private String code;
	private String message;

	public MessageResponse(String message) {
		this.message = message;
	}
	
	public MessageResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
