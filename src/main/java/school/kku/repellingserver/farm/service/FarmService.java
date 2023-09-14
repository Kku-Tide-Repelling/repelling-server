package school.kku.repellingserver.farm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.kku.repellingserver.farm.domain.Farm;
import school.kku.repellingserver.farm.dto.FarmRequest;
import school.kku.repellingserver.farm.repository.FarmRepository;
import school.kku.repellingserver.gateway.domain.Gateway;
import school.kku.repellingserver.gateway.repository.GatewayRepository;

@RequiredArgsConstructor
@Service
public class FarmService {

    private final FarmRepository farmRepository;
    private final GatewayRepository gatewayRepository;


    @Transactional
    public Farm save(FarmRequest farmRequest) {

        Farm farm = Farm.toEntity(farmRequest);
        farmRepository.save(farm);

        Gateway gateway = new Gateway(farmRequest.serialId());
        gatewayRepository.save(gateway);

        farm.setGateway(gateway);

        return farm;
    }

}
