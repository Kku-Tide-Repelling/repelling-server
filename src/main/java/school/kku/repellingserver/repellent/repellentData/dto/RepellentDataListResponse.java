package school.kku.repellingserver.repellent.repellentData.dto;

import school.kku.repellingserver.repellent.repellentData.domain.DetectionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RepellentDataListResponse(
    LocalDate detectedAt,
    DetectionType detectionType,
    Integer count
) {

    public static RepellentDataListResponse of(LocalDate detectedAt, DetectionType detectionType, Integer count) {
        return new RepellentDataListResponse(detectedAt, detectionType, count);
    }
}
