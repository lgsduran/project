package br.com.mesttra.project.service;

import static java.lang.String.format;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.mesttra.project.clients.BudgetClient;
import br.com.mesttra.project.clients.SecretaryClient;
import br.com.mesttra.project.entity.Project;
import br.com.mesttra.project.exception.BusinessException;
import br.com.mesttra.project.repository.ProjectRepository;
import br.com.mesttra.project.request.LoginRequest;
import br.com.mesttra.project.request.ProjectRequest;
import br.com.mesttra.project.response.BudgetResponse;
import br.com.mesttra.project.response.ChangeBudgetExpenses;
import br.com.mesttra.project.response.JwtResponse;
import br.com.mesttra.project.response.SecretaryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
@Service
public class ProjectServiceImpl implements IProjectService {
	
	private ProjectRepository projectRepository;
	private BudgetClient budgetClient;
	private SecretaryClient secretaryClient;

	@Override
	public Project addProject(ProjectRequest projectRequest) throws BusinessException {
		try {
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setUsername("teste");
			loginRequest.setPassword("teste");

			log.info("Adding project started.");
			JwtResponse budgetAuthorization = budgetClient.getBudgetAuthorization(loginRequest);
			List<BudgetResponse> budgets = budgetClient.getBudgets("Bearer "+budgetAuthorization.getToken()).stream()
					.filter(b -> b.getPossibleDestinations().equalsIgnoreCase(projectRequest.getFolder().toString()))
					.collect(Collectors.toList());

			double totalAmount = budgets.get(budgets.size() - 1).getTotalAmount();

			if (projectRequest.getCost() > totalAmount) {
				throw new BusinessException(
						format("Cost %s must be lower that Total Amout %s.", projectRequest.getCost(), totalAmount));
			}

			JwtResponse secretariasAuthorization = secretaryClient.getSecretariasAuthorization(loginRequest);
			SecretaryResponse secretaryById = secretaryClient.getSecretaryById(
					"Bearer "+secretariasAuthorization.getToken(),
					projectRequest.getSecretariatId());
			
			boolean isUnderInvestigation = secretaryById.isUnderInvestigation();
			if (isUnderInvestigation) {
				throw new BusinessException(
						format("Secretary %s is under investigation.", secretaryById.getSecretaryName()));
			}
			
			ChangeBudgetExpenses c = new ChangeBudgetExpenses();
			c.spentAmount = projectRequest.getCost();
			
			budgetClient.update(
					"Bearer "+budgetAuthorization.getToken(),
					budgets.get(budgets.size() - 1).getId(), c);
			log.info("Updated spent amount in Budget table.");
			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		log.info("Project created.");
		return projectRepository.save(projectRequest.toEntity());
	}

}
