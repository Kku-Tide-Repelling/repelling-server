package school.kku.repellingserver.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.kku.repellingserver.jwt.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
