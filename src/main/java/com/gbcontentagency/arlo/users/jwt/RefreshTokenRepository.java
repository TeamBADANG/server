package com.gbcontentagency.arlo.users.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Boolean existsByRefreshToken(String refreshToken);

    @Transactional
    Boolean deleteByRefreshToken(String refreshToken);

}
