package com.level.tech.repository;

import com.level.tech.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    @Query("SELECT t " +
            "FROM PasswordResetToken t " +
            "WHERE t.user.id = :id")
    Optional<PasswordResetToken> findByUserId(@Param("id") Long id);
}