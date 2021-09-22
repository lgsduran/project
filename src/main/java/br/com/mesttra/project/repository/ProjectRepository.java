package br.com.mesttra.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mesttra.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
