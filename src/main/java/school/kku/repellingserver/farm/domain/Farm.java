package school.kku.repellingserver.farm.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.kku.repellingserver.gateway.domain.Gateway;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Farm {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gateway gateway;
}
