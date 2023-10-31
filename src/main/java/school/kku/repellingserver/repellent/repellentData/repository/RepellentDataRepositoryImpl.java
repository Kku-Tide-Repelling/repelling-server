package school.kku.repellingserver.repellent.repellentData.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import school.kku.repellingserver.repellent.repellentData.dto.DayByDetectionListResponse;

import java.time.LocalDate;
import java.util.List;
import school.kku.repellingserver.repellent.repellentData.dto.MainPageDataResponse;
import school.kku.repellingserver.repellent.repellentData.dto.QMainPageDataResponse;

import static school.kku.repellingserver.repellent.repellentData.domain.QRepellentData.repellentData;

@RequiredArgsConstructor
public class RepellentDataRepositoryImpl implements RepellentDataRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    LocalDate fourDaysAgo = LocalDate.now().minusDays(4);
    LocalDate now = LocalDate.now();

    @Override
    public List<DayByDetectionListResponse> findRepellentDataByMemberGroupByFarm(Long farmId) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                DayByDetectionListResponse.class,
                                repellentData.detectionDate,
                                repellentData.detectionType,
                                repellentData.detectionNum.sum()
                        )
                )
                .from(repellentData)
                .where(
                        repellentData.repellentDevice.farm.id.eq(farmId)
                                .and(repellentData.detectionDate.between(fourDaysAgo, now))
                )
                .groupBy(repellentData.detectionDate, repellentData.detectionType)
                .orderBy(repellentData.detectionDate.desc()) // 최신 날짜 순으로 정렬
                .fetch();


    }

  @Override
  public MainPageDataResponse findRepellentDataByFarmGroupByRepellentSound(Long farmId) {
    return jpaQueryFactory.select(
            new QMainPageDataResponse(
                repellentData.reDetectionMinutes.avg(),
                repellentData.repellentSound.soundName
            )
        )
        .from(repellentData)
        .where(
            repellentData.repellentDevice.farm.id.eq(farmId)
                .and(repellentData.detectionDate.between(fourDaysAgo, now))
        )
        .groupBy(repellentData.repellentSound.soundName)
        .orderBy(repellentData.reDetectionMinutes.avg().desc())
        .fetchOne();
  }

}
