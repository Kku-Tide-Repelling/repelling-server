package school.kku.repellingserver.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.kku.repellingserver.gateway.dto.RepellentDataRequest;
import school.kku.repellingserver.gateway.repository.GatewayRepository;

@RequiredArgsConstructor
@Service
public class GatewayService {

    private final GatewayRepository gatewayRepository;


    public boolean isSerialIdExists(String serialId) {
        return gatewayRepository.existsBySerialId(serialId);
    }

    public void saveData(RepellentDataRequest request, String gatewayIp) {


    }
}
