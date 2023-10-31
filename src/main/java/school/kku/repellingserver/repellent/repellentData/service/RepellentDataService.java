package school.kku.repellingserver.repellent.repellentData.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.kku.repellingserver.gateway.domain.Gateway;
import school.kku.repellingserver.gateway.dto.RepellentDataRequest;
import school.kku.repellingserver.gateway.repository.GatewayRepository;
import school.kku.repellingserver.repellent.repellentData.domain.RepellentData;
import school.kku.repellingserver.repellent.repellentData.domain.RepellentSound;
import school.kku.repellingserver.repellent.repellentData.dto.DayByDetectionListResponse;
import school.kku.repellingserver.repellent.repellentData.dto.MainPageDataResponse;
import school.kku.repellingserver.repellent.repellentData.repository.RepellentDataRepository;

import java.util.List;
import school.kku.repellingserver.repellent.repellentData.repository.RepellentSoundRepository;
import school.kku.repellingserver.repellent.repellentDevice.domain.RepellentDevice;
import school.kku.repellingserver.repellent.repellentDevice.repository.RepellentDeviceRepository;

@RequiredArgsConstructor
@Service
public class RepellentDataService {

    private final RepellentDataRepository repellentDataRepository;
    private final GatewayRepository gatewayRepository;
    private final RepellentSoundRepository repellentSoundRepository;
    private final RepellentDeviceRepository repellentDeviceRepository;

    public MainPageDataResponse getRepellentDataList(Long farmId) {

        List<DayByDetectionListResponse> dayByDataList = repellentDataRepository.findRepellentDataByMemberGroupByFarm(
            farmId);

        MainPageDataResponse response = repellentDataRepository.findRepellentDataByFarmGroupByRepellentSound(
            farmId);

        response.setDayByDetectionList(dayByDataList);

        return response;
    }

    @Transactional
    public RepellentData saveData(RepellentDataRequest request, String gatewayIp) {

        boolean isGatewayExists = gatewayRepository.existsBySerialId(request.gatewayId());

        if (isGatewayExists) {
            gatewayRepository.findBySerialId(request.gatewayId())
                .ifPresent(gateway -> {
                    gateway.setIpv4(gatewayIp);
                });

        } else {
            gatewayRepository.save(
                Gateway.builder()
                    .serialId(request.gatewayId())
                    .ipv4(gatewayIp)
                    .build()
            );

        }

        boolean isRepellentSoundExists = repellentSoundRepository.existsBySoundNameAndSoundLevel(request.soundType(), request.soundLevel());

        if (isRepellentSoundExists) {
            RepellentSound repellentSound = repellentSoundRepository.findBySoundNameAndSoundLevel(request.soundType(), request.soundLevel()).get();
            RepellentDevice repellentDevice = repellentDeviceRepository.findBySerialId(request.nodeId())
                .orElseThrow(() -> new RuntimeException("Repellent Device not found"));

            RepellentData recentRepellentData = repellentDataRepository.findFirstByOrderByIdDesc()
                .orElse(RepellentData.builder().detectionTime(request.timestamp()).build());

            Duration duration = Duration.between(request.timestamp(),
                recentRepellentData.getDetectionTime());

            return repellentDataRepository.save(
                RepellentData.builder()
                    .detectionType(request.detectionType())
                    .detectionTime(request.timestamp())
                    .detectionDate(request.timestamp().toLocalDate())
                    .repellentDevice(repellentDevice)
                    .repellentSound(repellentSound)
                    .reDetectionMinutes(duration.toMinutes())
                    .detectionNum(request.detectedCount())
                    .build()
            );
        } else {

            RepellentSound repellentSound = repellentSoundRepository.save(RepellentSound.builder()
                .soundName(request.soundType())
                .soundLevel(request.soundLevel())
                .build()
            );
            RepellentDevice repellentDevice = repellentDeviceRepository.findBySerialId(request.nodeId())
                .orElseThrow(() -> new RuntimeException("Repellent Device not found"));

            RepellentData recentRepellentData = repellentDataRepository.findFirstByOrderByIdDesc()
                .orElse(RepellentData.builder().detectionTime(request.timestamp()).build());

            Duration duration = Duration.between(request.timestamp(),
                recentRepellentData.getDetectionTime());

            return repellentDataRepository.save(
                RepellentData.builder()
                    .detectionType(request.detectionType())
                    .detectionTime(request.timestamp())
                    .detectionDate(request.timestamp().toLocalDate())
                    .repellentDevice(repellentDevice)
                    .repellentSound(repellentSound)
                    .reDetectionMinutes(duration.toMinutes())
                    .detectionNum(request.detectedCount())
                    .build()
            );
        }
    }
}
