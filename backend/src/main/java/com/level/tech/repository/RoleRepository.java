package com.level.tech.repository;

import com.level.tech.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r " +
            "FROM Role r " +
            "WHERE r.name = :name")
    Role findByName(@Param("name") String roleName);
}
