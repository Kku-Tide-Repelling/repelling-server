package school.kku.repellingserver.repellent.repellentData.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import school.kku.repellingserver.repellent.repellentData.dto.RepellentDataListResponse;

import java.time.LocalDate;
import java.util.List;

import static school.kku.repellingserver.repellent.repellentData.domain.QRepellentData.repellentData;

@RequiredArgsConstructor
public class RepellentDataRepositoryImpl implements RepellentDataRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
    LocalDate now = LocalDate.now();

    @Override
    public List<RepellentDataListResponse> findRepellentDataByMemberGroupByFarm(Long farmId) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                RepellentDataListResponse.class,
                                repellentData.detectionDate,
                                repellentData.detectionType,
                                repellentData.detectionNum.sum()
                        )
                )
                .from(repellentData)
                .where(
                        repellentData.repellentDevice.farm.id.eq(farmId)
                                .and(repellentData.detectionDate.between(fiveDaysAgo, now))
                )
                .groupBy(repellentData.detectionDate)
                .orderBy(repellentData.detectionDate.desc()) // 최신 날짜 순으로 정렬
                .fetch();


    }
}
