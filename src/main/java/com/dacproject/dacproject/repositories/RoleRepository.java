package com.dacproject.dacproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dacproject.dacproject.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
