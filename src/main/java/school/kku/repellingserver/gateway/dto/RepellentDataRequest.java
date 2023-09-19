package school.kku.repellingserver.gateway.dto;

import java.time.LocalDateTime;

public record RepellentDataRequest (
        String gatewayId,
        String nodeId,
        String message,
        String soundType,
        String soundLevel,
        LocalDateTime timestamp
) {
    public static RepellentDataRequest of(String gatewayId, String nodeId, String message, String soundType, String soundLevel, LocalDateTime timestamp) {
        return new RepellentDataRequest(gatewayId, nodeId, message, soundType, soundLevel, timestamp);
    }
}
