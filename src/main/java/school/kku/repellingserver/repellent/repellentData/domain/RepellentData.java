package school.kku.repellingserver.repellent.repellentData.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.kku.repellingserver.repellent.repellentDevice.domain.RepellentDevice;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RepellentData {

    @Id @GeneratedValue
    private Long id;

    private DetectionType detectionType;

    private Integer detectionNum;

    private LocalDateTime detectionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private RepellentDevice repellentDevice;
}
