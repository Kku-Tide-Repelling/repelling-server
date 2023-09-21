package school.kku.repellingserver.gateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RepellentDataRequest (
        String gatewayId,
        String nodeId,
        String message,
        String soundType,
        String soundLevel,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime timestamp
) {
    public static RepellentDataRequest of(String gatewayId, String nodeId, String message, String soundType, String soundLevel, LocalDateTime timestamp) {
        return new RepellentDataRequest(gatewayId, nodeId, message, soundType, soundLevel, timestamp);
    }
}
