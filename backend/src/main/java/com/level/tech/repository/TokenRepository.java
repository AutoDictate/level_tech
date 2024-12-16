package com.level.tech.repository;

import com.level.tech.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    // t is alias for Token
    // u is alias for User
    @Query("SELECT t " +
            "FROM Token t " +
            "INNER JOIN t.user u " +
            "WHERE u.id = :userId " +
            "AND (t.expired = false OR t.revoked = false)"
    )
    List<Token> findAllUserAccessTokens(Long userId);

    Optional<Token> findByJwtToken(String jwt);

    void deleteAllByUserId(Long userId);
}
