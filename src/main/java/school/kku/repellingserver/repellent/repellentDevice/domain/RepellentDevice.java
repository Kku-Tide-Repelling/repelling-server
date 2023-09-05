package school.kku.repellingserver.repellent.repellentDevice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.kku.repellingserver.farm.domain.Farm;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RepellentDevice {

    @Id @GeneratedValue
    private Long id;

    private String serialId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Farm farm;
}
