package com.n26.infrastructure.controller.getstatistics;

import com.n26.infrastructure.controller.ControllerTest;
import com.n26.usecase.getstatistics.GetStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.net.URI;

import static com.n26.usecase.getstatistics.GetStatisticsResponse.mapToGetStatisticsResponse;
import static com.n26.utils.DomainFactoryUtils.createStatistics;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetStatisticsControllerTest extends ControllerTest {

  @MockBean
  private GetStatistics getStatistics;

  @Test
  void shouldReturnExpectedJson() throws Exception {
    when(getStatistics.getStatistics())
        .thenReturn(mapToGetStatisticsResponse(createStatistics("24.468", "12.234", "12.234", 2)));

    mockMvc
        .perform(get(new URI("/statistics")))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(
            "{" +
                "\"sum\":\"24.47\"," +
                "\"avg\":\"12.23\"," +
                "\"max\":\"12.23\"," +
                "\"min\":\"12.23\"," +
                "\"count\":2" +
                "}"));
  }
}