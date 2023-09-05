package school.kku.repellingserver.gateway.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.kku.repellingserver.user.domain.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Gateway {

    @Id @GeneratedValue
    private Long id;

    private String serialId;

    private String ipv4;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
