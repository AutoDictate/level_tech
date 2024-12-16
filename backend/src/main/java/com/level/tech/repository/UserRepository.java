package com.level.tech.repository;

import com.level.tech.entity.Role;
import com.level.tech.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
           "WHERE (LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(u.phoneNo) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND (:role IS NULL OR u.role = :role) " +
           "AND u.isDeleted = false")
    Page<User> searchUsers(@Param("search") String search, @Param("role") Role role, Pageable pageable);


    @Query("SELECT u FROM User u " +
            "WHERE (LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.phoneNo) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<User> searchUsers(@Param("search") String search, Pageable pageable);

    Optional<User> findByEmail(String username);

    Page<User> findAllUserByRole(Role role, Pageable pageable);

    Boolean existsByPhoneNo(String phoneNo);

    Boolean existsByEmail(String email);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.role = :role")
    Page<User> findAllByRole(@Param("role") Role role, Pageable pageable);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.role = :role")
    List<User> findAllByRole(Role role);
}
