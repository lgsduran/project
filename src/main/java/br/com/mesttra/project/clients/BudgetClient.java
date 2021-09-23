package br.com.mesttra.project.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.mesttra.project.request.LoginRequest;
import br.com.mesttra.project.response.BudgetResponse;
import br.com.mesttra.project.response.ChangeBudgetExpenses;
import br.com.mesttra.project.response.JwtResponse;

@FeignClient(name = "budget-client", url = "http://localhost:8081")
public interface BudgetClient {

	@GetMapping("/budgets/budgets")
	List<BudgetResponse> getBudgets(@RequestHeader("Authorization") String bearerToken);

//	@Headers("Authorization: {access_token}")
	@PatchMapping("budgets/{id}/expense")
	ChangeBudgetExpenses update(
			@RequestHeader("Authorization") String bearerToken,
			@PathVariable("id") int id, 
			@RequestBody ChangeBudgetExpenses changeBudgetExpenses);
	
	@PostMapping("/budget/auth/signin")
	JwtResponse getBudgetAuthorization(LoginRequest loginRequest);

}
