package com.jeiyuen.ecommerce.repository;

import java.util.Optional;

import com.jeiyuen.ecommerce.model.Role;
import com.jeiyuen.ecommerce.model.SystemRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByRoleName(SystemRole roleUser);

}
