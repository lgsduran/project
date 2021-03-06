package br.com.mesttra.project.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.mesttra.project.response.SecretaryResponse;

@FeignClient(name = "secretary-client", url = "http://localhost:8082")
public interface SecretaryClient {
	
	@GetMapping("/secretarias/{id}")
	SecretaryResponse getSecretaryById(
			@RequestHeader("Authorization") String bearerToken,
			@PathVariable(value = "id") int id);

}
