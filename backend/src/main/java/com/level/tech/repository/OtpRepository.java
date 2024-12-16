package com.level.tech.repository;

import com.level.tech.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    @Query("SELECT o " +
            "FROM Otp o " +
            "WHERE o.user.id = :user"
    )
    Optional<Otp> findByUserId(@Param("user") Long userId);

    boolean existsByUserId(Long userId);
}
