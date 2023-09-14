package school.kku.repellingserver.farm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import school.kku.repellingserver.common.BaseControllerTest;
import school.kku.repellingserver.farm.constant.FarmType;
import school.kku.repellingserver.farm.dto.FarmRequest;
import school.kku.repellingserver.farm.service.FarmService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FarmControllerTest extends BaseControllerTest {

    @MockBean
    FarmService farmService;

    @Test
    void 농장을_저장한다() throws Exception {
        //given
        FarmRequest farmRequest = FarmRequest.of(
                "serialId",
                "farmName",
                "farmAddress",
                "latitude",
                "longitude",
                FarmType.RICE_FARM
        );

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/farm")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(farmRequest))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("farm/create/success",
                        requestFields(
                                fieldWithPath("serialId").description("게이트웨이 serialId"),
                                fieldWithPath("farmName").description("게이트웨이 serialId"),
                                fieldWithPath("farmAddress").description("게이트웨이 serialId"),
                                fieldWithPath("latitude").description("게이트웨이 serialId"),
                                fieldWithPath("longitude").description("게이트웨이 serialId"),
                                fieldWithPath("farmType").description("게이트웨이 serialId")
                                )));
        
    }

}