package br.com.mesttra.project.service;

import br.com.mesttra.project.entity.Project;
import br.com.mesttra.project.exception.BusinessException;
import br.com.mesttra.project.request.ProjectRequest;

public interface IProjectService {
	
	Project addProject(ProjectRequest projectRequest) throws BusinessException;

}
