package br.com.mesttra.project.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.BeanUtils;

import br.com.mesttra.project.entity.Project;
import br.com.mesttra.project.enums.Folder;
import lombok.Data;

@Data
public class ProjectRequest {

	private int secretariatId;

	private Double cost;

	@NotEmpty
	private String title;

	@NotEmpty
	private String description;

	@Enumerated(EnumType.STRING)
	private Folder folder;

	public Project toEntity() {
		Project project = new Project();
		BeanUtils.copyProperties(this, project);
		return project;
	}

}
