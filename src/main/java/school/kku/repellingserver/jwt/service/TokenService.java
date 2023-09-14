package school.kku.repellingserver.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.kku.repellingserver.jwt.domain.RefreshToken;
import school.kku.repellingserver.jwt.repository.RefreshTokenRepository;
import school.kku.repellingserver.jwt.util.JwtTokenUtils;
import school.kku.repellingserver.member.service.MemberService;

import static school.kku.repellingserver.jwt.constants.TokenDuration.ACCESS_TOKEN_DURATION;
import static school.kku.repellingserver.jwt.constants.TokenDuration.REFRESH_TOKEN_DURATION;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateAccessToken(String loginId) {
        return JwtTokenUtils.generateAccessToken(loginId, secretKey, ACCESS_TOKEN_DURATION.getDuration());
    }

    public String generateRefreshToken(String loginId) {
        String refreshToken = JwtTokenUtils.generateAccessToken(loginId, secretKey, REFRESH_TOKEN_DURATION.getDuration());
        saveRefreshToken(loginId, refreshToken);

        return refreshToken;
    }

    private void saveRefreshToken(String loginId, String refreshToken) {
        refreshTokenRepository.save(RefreshToken.builder()
                .member(memberService.loadUserByUsername(loginId))
                .refreshToken(refreshToken)
                .build());
    }
}
