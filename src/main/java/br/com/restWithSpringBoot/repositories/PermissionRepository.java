package br.com.restWithSpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.restWithSpringBoot.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
