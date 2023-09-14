package school.kku.repellingserver.gateway.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import school.kku.repellingserver.common.BaseControllerTest;
import school.kku.repellingserver.gateway.service.GatewayService;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GatewayControllerTest extends BaseControllerTest {

    @MockBean
    GatewayService gatewayService;

    @Test
    void 게이트웨이_serialId_가_존재하면_true를_리턴한다() throws Exception {
        //given
        final String serialId = "serialId";

        when(gatewayService.isSerialIdExists(serialId))
                .thenReturn(true);

        //when
        ResultActions resultActions = mockMvc.perform(get(API + "/valid/serial-id")
                        .param("serialId", serialId))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("gateway/serial-id/exists/success",
                        queryParameters(
                                parameterWithName("serialId").description("게이트웨이의 serialId")
                        ),
                        responseFields(
                                fieldWithPath("isSerialIdExists").description("게이트웨이의 serialId가 존재하는지 여부")
                        )
                        ));

    }

}