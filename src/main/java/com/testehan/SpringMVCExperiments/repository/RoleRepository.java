package com.testehan.SpringMVCExperiments.repository;

import com.testehan.SpringMVCExperiments.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
