package school.kku.repellingserver.repellent.repellentData.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import school.kku.repellingserver.repellent.repellentDevice.domain.RepellentDevice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RepellentData {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private DetectionType detectionType;

//    @Column(columnDefinition = "int default 0")
    @ColumnDefault("0")
    private Integer detectionNum;

    private LocalDateTime detectionTime;

    private LocalDate detectionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private RepellentDevice repellentDevice;

    @ManyToOne(fetch = FetchType.EAGER)
    private RepellentSound repellentSound;

    @Builder
    public RepellentData(Long id, DetectionType detectionType, Integer detectionNum, LocalDateTime detectionTime, LocalDate detectionDate, RepellentDevice repellentDevice, RepellentSound repellentSound) {
        this.id = id;
        this.detectionType = detectionType;
        this.detectionNum = detectionNum;
        this.detectionTime = detectionTime;
        this.detectionDate = detectionDate;
        this.repellentDevice = repellentDevice;
        this.repellentSound = repellentSound;
    }
}
