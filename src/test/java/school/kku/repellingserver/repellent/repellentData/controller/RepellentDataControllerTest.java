package school.kku.repellingserver.repellent.repellentData.controller;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import school.kku.repellingserver.common.BaseControllerTest;
import school.kku.repellingserver.gateway.dto.RepellentDataRequest;
import school.kku.repellingserver.repellent.repellentData.domain.DetectionType;
import school.kku.repellingserver.repellent.repellentData.dto.DayByDetectionListResponse;
import school.kku.repellingserver.repellent.repellentData.dto.MainPageDataResponse;
import school.kku.repellingserver.repellent.repellentData.service.RepellentDataService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RepellentDataControllerTest extends BaseControllerTest {

    @MockBean
    private RepellentDataService repellentDataService;

    @Test
    void 메인_페이지에_필요한_퇴치_데이터를_리턴한다() throws Exception {
        //given
        DayByDetectionListResponse response1 = DayByDetectionListResponse.of(
                LocalDate.of(2023, 8, 20),
                DetectionType.PIR,
                10
        );
        DayByDetectionListResponse response2 = DayByDetectionListResponse.of(
                LocalDate.of(2023, 8, 21),
                DetectionType.PIR,
                5
        );
        DayByDetectionListResponse response3 = DayByDetectionListResponse.of(
                LocalDate.of(2023, 8, 22),
                DetectionType.PIR,
                5
        );

        when(repellentDataService.getRepellentDataList(any()))
            .thenReturn(MainPageDataResponse.of(
                List.of(response1, response2, response3),
                13L,
                "소리명"
            ));

        //when
        ResultActions resultActions = mockMvc.perform(get(API + "/repellent-data/main")
                .param("farmId", "1")
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("repellent-data/main/success",
                        responseFields(
                                fieldWithPath("dayByDetectionList[].detectedAt").description("퇴치 데이터가 측정된 날짜"),
                                fieldWithPath("dayByDetectionList[].detectionType").description("측정된 타입"),
                                fieldWithPath("dayByDetectionList[].count").description("측정된 횟수"),
                                fieldWithPath("reDetectionTimeAvg").description("재탐지 시간 평균"),
                                fieldWithPath("repellentSoundName").description("퇴치기 소리명")
                        )));

    }


    @Test
    void 게이트웨이에서_퇴치_데이터를_전달받는다() throws Exception {
        //given
        final RepellentDataRequest request = RepellentDataRequest.of(
            "gatewayId",
            "nodeId",
            "message",
            "soundType",
            3,
            LocalDateTime.now(),
            DetectionType.PIR,
            1
        );

        System.out.println(objectMapper.writeValueAsString(request));

        //when
        ResultActions resultActions = mockMvc.perform(post(API + "/repellent-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
            .andDo(document("repellent-data/success",
                requestFields(
                    fieldWithPath("gatewayId").description("게이트웨이의 ID"),
                    fieldWithPath("nodeId").description("노드의 ID"),
                    fieldWithPath("message").description("메시지"),
                    fieldWithPath("soundType").description("소리의 종류"),
                    fieldWithPath("soundLevel").description("소리의 크기"),
                    fieldWithPath("timestamp").description("데이터가 전송된 시간"),
                    fieldWithPath("detectionType").description("감지된 타입"),
                    fieldWithPath("detectedCount").description("감지된 횟수")
                )));
    }


}