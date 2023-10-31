package school.kku.repellingserver.repellent.repellentData.repository;

import school.kku.repellingserver.repellent.repellentData.dto.DayByDetectionListResponse;

import java.util.List;
import school.kku.repellingserver.repellent.repellentData.dto.MainPageDataResponse;

public interface RepellentDataRepositoryCustom {

    List<DayByDetectionListResponse> findRepellentDataByMemberGroupByFarm(Long farmId);

    MainPageDataResponse findRepellentDataByFarmGroupByRepellentSound(Long farmId);

}
