package com.n26.infrastructure.controller.getstatistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.usecase.getstatistics.GetStatisticsResponse;
import org.junit.jupiter.api.Test;

import static com.n26.infrastructure.controller.getstatistics.GetStatisticsControllerResponse.mapFrom;
import static com.n26.usecase.getstatistics.GetStatisticsResponse.mapToGetStatisticsResponse;
import static com.n26.utils.DomainFactoryUtils.createStatistics;
import static org.assertj.core.api.Assertions.assertThat;

class GetStatisticsControllerResponseTest {

  private final GetStatisticsResponse getStatisticsResponse =
      mapToGetStatisticsResponse(createStatistics("24.468", "12.234", "12.234", 2));


  @Test
  void shouldMapToExpected() throws JsonProcessingException {

    final GetStatisticsControllerResponse actual = mapFrom(getStatisticsResponse);

    final GetStatisticsControllerResponse expected =
        new GetStatisticsControllerResponse("24.47", "12.23", "12.23", "12.23", 2);
    assertThat(actual)
        .isEqualToComparingFieldByField(expected);
    final String actualAsJson = new ObjectMapper().writeValueAsString(actual);
    assertThat(actualAsJson)
        .isEqualToIgnoringWhitespace(
            "{" +
                "\"sum\":\"24.47\"," +
                "\"avg\":\"12.23\"," +
                "\"max\":\"12.23\"," +
                "\"min\":\"12.23\"," +
                "\"count\":2" +
                "}");
  }
}