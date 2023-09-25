package school.kku.repellingserver.repellent.repellentData.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.kku.repellingserver.member.domain.Member;
import school.kku.repellingserver.repellent.repellentData.dto.RepellentDataListResponse;
import school.kku.repellingserver.repellent.repellentData.repository.RepellentDataRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RepellentDataService {

    private final RepellentDataRepository repellentDataRepository;


    public List<RepellentDataListResponse> getRepellentDataList(Member member, Long farmId) {

        return repellentDataRepository.findRepellentDataByMemberGroupByFarm(farmId);
    }
}
