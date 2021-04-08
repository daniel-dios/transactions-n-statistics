package com.n26.usecase.getstatistics;

import com.n26.domain.Statistics;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.n26.utils.DomainFactoryUtils.createStatistics;
import static org.assertj.core.api.Assertions.assertThat;

class GetStatisticsResponseTest {

  @Test
  void shouldMapFromStatisticsToGetStatisticsResponse() {
    final Statistics statistics = createStatistics("17.0000", "6.0000", "1.0000", 3);

    final GetStatisticsResponse actual = GetStatisticsResponse.mapToGetStatisticsResponse(statistics);

    final GetStatisticsResponse expected = new GetStatisticsResponse(
        new BigDecimal("17.00"),
        new BigDecimal("5.67"),
        new BigDecimal("6.00"),
        new BigDecimal("1.00"),
        3 // 6 + 6 + 1
    );

    assertThat(actual).isEqualToComparingFieldByField(expected);
  }
}