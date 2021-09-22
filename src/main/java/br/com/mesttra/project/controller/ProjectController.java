package br.com.mesttra.project.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mesttra.project.clients.BudgetClient;
import br.com.mesttra.project.entity.Project;
import br.com.mesttra.project.exception.BusinessException;
import br.com.mesttra.project.request.ProjectRequest;
import br.com.mesttra.project.service.ProjectServiceImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectController {
	
	ProjectServiceImpl projectService;
	BudgetClient budgetClient;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Project addSecretarias(@Valid @RequestBody ProjectRequest projectRequest) 
			throws BusinessException {
		return this.projectService.addProject(projectRequest);
	}

}
