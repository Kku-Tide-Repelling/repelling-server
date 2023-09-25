package school.kku.repellingserver.farm.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import school.kku.repellingserver.common.BaseControllerTest;
import school.kku.repellingserver.farm.constant.FarmType;
import school.kku.repellingserver.farm.domain.Farm;
import school.kku.repellingserver.farm.dto.FarmListResponse;
import school.kku.repellingserver.farm.dto.FarmRequest;
import school.kku.repellingserver.farm.service.FarmService;
import school.kku.repellingserver.gateway.domain.Gateway;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                FarmType.RICE_FARM
        );

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/farm")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(farmRequest))
        ).andDo(print());

        //then
        resultActions.andExpect(status().isCreated())
                .andDo(document("farm/create/success",
                        requestFields(
                                fieldWithPath("serialId").description("게이트웨이 serialId"),
                                fieldWithPath("farmName").description("농장 이름"),
                                fieldWithPath("farmAddress").description("농장 주소"),
                                fieldWithPath("farmType").description("농장이 어떤 농장인지 타입 ONCHARD(\"과수원\"), GINGSENG_FIELD(\"인삼밭\"), RICE_FARM(\"벼농장\")")
                                )));
        
    }

    @Test
    void 설정페이지에_필요한_농장_리스트를_반환한다() throws Exception {
        //given
        FarmListResponse response1 = FarmListResponse.of(
                126L,
                "farmName",
                10L,
                "농장 주소"
        );

        FarmListResponse response2 = FarmListResponse.of(
                126L,
                "farmName2",
                10L,
                "농장 주소 2"
        );

        when(farmService.getFarmListResponseList(any())).thenReturn(List.of(response1, response2));
        //when
        ResultActions resultActions = mockMvc.perform(get(API + "/farm/setting/list")
                        .header(AUTHORIZATION, "Bearer accessToken"))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("farm/setting/list/success",
                        responseFields(
                                fieldWithPath("[].farmId").description("농장 id"),
                                fieldWithPath("[].farmName").description("농장 이름"),
                                fieldWithPath("[].deviceCount").description("농장 멤버 수"),
                                fieldWithPath("[].farmAddress").description("농장 주소")
                        )));
    }

    @Test
    void 메인페이지에서_사용할_농장_리스트를_반환한다() throws Exception {
        //given
        Farm farm1 = Farm.builder()
                .id(1L)
                .name("farmName")
                .address("농장 주소")
                .farmType(FarmType.ONCHARD)
                .gateway(
                        Gateway.builder()
                                .id(1L)
                                .serialId("205")
                                .ipv4("123.123.123.123")
                                .isActivated(false).build()
                )
                .build();

        Farm farm2 = Farm.builder()
                .id(2L)
                .name("farmName2")
                .address("농장 주소 2")
                .farmType(FarmType.RICE_FARM)
                .gateway(
                        Gateway.builder()
                                .id(1L)
                                .serialId("205")
                                .ipv4("123.123.123.123")
                                .isActivated(false).build()
                )
                .build();

        when(farmService.getFarmList(any())).thenReturn(List.of(farm1, farm2));
        //when
        ResultActions resultActions = mockMvc.perform(get(API + "/farm/list")
                        .header(AUTHORIZATION, "Bearer accessToken"))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("farm/list/success",
                        responseFields(
                                fieldWithPath("[].id").description("농장 id"),
                                fieldWithPath("[].name").description("농장 이름"),
                                fieldWithPath("[].address").description("농장 주소"),
                                fieldWithPath("[].farmType").description("농장 타입"),
                                fieldWithPath("[].gateway.id").description("게이트웨이 id"),
                                fieldWithPath("[].gateway.serialId").description("게이트웨이 serialId"),
                                fieldWithPath("[].gateway.ipv4").description("게이트웨이 ipv4"),
                                fieldWithPath("[].gateway.isActivated").description("게이트웨이 활성화 여부")
                        )));
    }



}