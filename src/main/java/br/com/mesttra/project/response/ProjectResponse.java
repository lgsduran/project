package br.com.mesttra.project.response;

import org.springframework.beans.BeanUtils;

import br.com.mesttra.project.entity.Project;
import br.com.mesttra.project.enums.Folder;
import lombok.Data;

@Data
public class ProjectResponse {

	private int secretariatId;

	private double cost;

	private String title;

	private String description;

	private Folder folder;

	public Project toEntity() {
		Project project = new Project();
		BeanUtils.copyProperties(this, project);
		return project;
	}

}
