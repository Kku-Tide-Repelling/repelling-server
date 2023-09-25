package school.kku.repellingserver.repellent.repellentData.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import school.kku.repellingserver.common.BaseControllerTest;
import school.kku.repellingserver.repellent.repellentData.domain.DetectionType;
import school.kku.repellingserver.repellent.repellentData.dto.RepellentDataListResponse;
import school.kku.repellingserver.repellent.repellentData.service.RepellentDataService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RepellentDataControllerTest extends BaseControllerTest {

    @MockBean
    private RepellentDataService repellentDataService;

    @Test
    void 메인_페이지에_필요한_퇴치_데이터를_리턴한다() throws Exception {
        //given
        RepellentDataListResponse response1 = RepellentDataListResponse.of(
                LocalDate.of(2023, 8, 20),
                DetectionType.PIR,
                10
        );
        RepellentDataListResponse response2 = RepellentDataListResponse.of(
                LocalDate.of(2023, 8, 21),
                DetectionType.PIR,
                5
        );
        RepellentDataListResponse response3 = RepellentDataListResponse.of(
                LocalDate.of(2023, 8, 22),
                DetectionType.PIR,
                5
        );


        when(repellentDataService.getRepellentDataList(any(), any()))
            .thenReturn(List.of(response1, response2, response3));

        //when
        ResultActions resultActions = mockMvc.perform(get(API + "/repellent-data/main")
                .param("farmId", "1")
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("repellent-data/main/success",
                        responseFields(
                                fieldWithPath("[].detectedAt").description("퇴치 데이터가 측정된 날짜"),
                                fieldWithPath("[].detectionType").description("측정된 타입"),
                                fieldWithPath("[].count").description("측정된 횟수")

                        )));

    }

}