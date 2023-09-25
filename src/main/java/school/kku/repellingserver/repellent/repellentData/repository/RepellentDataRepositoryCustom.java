package school.kku.repellingserver.repellent.repellentData.repository;

import school.kku.repellingserver.repellent.repellentData.dto.RepellentDataListResponse;

import java.util.List;

public interface RepellentDataRepositoryCustom {

    List<RepellentDataListResponse> findRepellentDataByMemberGroupByFarm(Long farmId);
}
