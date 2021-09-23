package br.com.mesttra.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mesttra.project.entity.Role;
import br.com.mesttra.project.enums.ERole;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(ERole name);

}
