package com.level.tech.repository;

import com.level.tech.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    boolean existsByName(String name);

}
