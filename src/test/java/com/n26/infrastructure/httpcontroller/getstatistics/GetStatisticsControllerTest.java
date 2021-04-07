package com.n26.infrastructure.httpcontroller.getstatistics;

import com.n26.Application;
import com.n26.usecase.getstatistics.GetStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.net.URI;

import static com.n26.usecase.getstatistics.GetStatisticsResponse.mapToGetStatisticsResponse;
import static com.n26.utils.DomainFactoryUtils.createStatistics;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
class GetStatisticsControllerTest {

  @Autowired
  private GenericWebApplicationContext webApplicationContext;

  @MockBean
  private GetStatistics getStatistics;

  private MockMvc mockMvc;

  @BeforeEach
  public void getContext() {
    mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void shouldReturnExpectedJson() throws Exception {
    when(getStatistics.getStatistics())
        .thenReturn(mapToGetStatisticsResponse(createStatistics("24.468", "12.234", "12.234", 2)));

    mockMvc.perform(get(new URI("/statistics")))
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