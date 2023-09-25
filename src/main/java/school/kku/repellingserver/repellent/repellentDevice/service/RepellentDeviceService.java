package school.kku.repellingserver.repellent.repellentDevice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.kku.repellingserver.repellent.repellentDevice.domain.RepellentDevice;
import school.kku.repellingserver.repellent.repellentDevice.repository.RepellentDeviceRepository;

@RequiredArgsConstructor
@Service
public class RepellentDeviceService {

    private final RepellentDeviceRepository repellentDeviceRepository;


    public boolean isSerialIdExistsActivated(String serialId) {
        boolean isActivate = repellentDeviceRepository.existsBySerialIdAndIsActivatedIsFalse(serialId);

        if (!isActivate) {
            return false;
        } else {
            RepellentDevice repellentDevice = repellentDeviceRepository.findBySerialId(serialId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시리얼 아이디입니다."));
            repellentDevice.activate();
            return true;
        }
    }
}
