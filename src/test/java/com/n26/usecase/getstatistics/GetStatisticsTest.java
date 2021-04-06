package com.n26.usecase.getstatistics;

import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;

import static com.n26.domain.Statistics.EMPTY_STATISTICS;
import static com.n26.usecase.getstatistics.GetStatisticsResponse.mapToGetStatisticsResponse;
import static com.n26.utils.DomainFactoryUtils.createStatistics;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetStatisticsTest {

  @Test
  void shouldReturnEmptyStatisticsWhenThereIsNoStatisticsInRepo() {
    final StatisticsRepository statisticsRepository = Mockito.mock(StatisticsRepository.class);
    when(statisticsRepository.getStatistics()).thenReturn(emptyList());
    final GetStatistics getStatistics = new GetStatistics(statisticsRepository);

    final GetStatisticsResponse actual = getStatistics.getStatistics();

    assertThat(actual)
        .isEqualToComparingFieldByField(mapToGetStatisticsResponse(EMPTY_STATISTICS));
  }

  @Test
  void shouldReturnAggregateStatisticsWhenThereIsStatisticsInRepo() {
    final StatisticsRepository statisticsRepository = Mockito.mock(StatisticsRepository.class);
    final List<Statistics> statisticsList = getStatisticsList(
        createStatistics("5.00", "3.00", "2.00", 2),
        createStatistics("123.75", "122.00", "1.75", 10),
        createStatistics("111.00", "1.00", "1.00", 111));
    final Statistics expected =
        createStatistics("239.75", "122.00", "1.00", 123);
    when(statisticsRepository.getStatistics()).thenReturn(statisticsList);
    final GetStatistics getStatistics = new GetStatistics(statisticsRepository);

    final GetStatisticsResponse actual = getStatistics.getStatistics();

    assertThat(actual)
        .isEqualToComparingFieldByField(mapToGetStatisticsResponse(expected));
  }

  private List<Statistics> getStatisticsList(Statistics... statistics) {
    return stream(statistics)
        .collect(Collectors.toList());
  }
}
