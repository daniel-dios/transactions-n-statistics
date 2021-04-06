package com.n26.usecase.getstatistics;

import com.n26.domain.Amount;
import com.n26.domain.Count;
import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static com.n26.domain.Statistics.EMPTY_STATISTICS;
import static java.util.Arrays.stream;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetStatisticsTest {

  @Test
  void shouldReturnEmptyStatisticsWhenThereIsNoStatisticsInRepo() {
    final StatisticsRepository statisticsRepository = Mockito.mock(StatisticsRepository.class);
    when(statisticsRepository.getStatistics()).thenReturn(emptySet());
    final GetStatistics getStatistics = new GetStatistics(statisticsRepository);

    final Statistics actual = getStatistics.getStatistics();

    assertThat(actual)
        .isEqualToComparingFieldByField(EMPTY_STATISTICS);
  }

  @Test
  void shouldReturnAggregateStatisticsWhenThereIsStatisticsInRepo() {
    final StatisticsRepository statisticsRepository = Mockito.mock(StatisticsRepository.class);
    final Set<Statistics> statisticsSet = getStatisticsSet(
        createStatistics("5.00", "2.5", "3.00", "2.00", 2),
        createStatistics("123.75", "2.5", "122.00", "1.75", 10),
        createStatistics("111.00", "1.0", "1.00", "1.00", 111));
    final Statistics expected =
        createStatistics("239.75", "1.95", "122.00", "1.00", 123);
    when(statisticsRepository.getStatistics()).thenReturn(statisticsSet);
    final GetStatistics getStatistics = new GetStatistics(statisticsRepository);

    final Statistics actual = getStatistics.getStatistics();

    assertThat(actual)
        .isEqualToComparingFieldByField(expected);
  }

  private Set<Statistics> getStatisticsSet(Statistics... statistics) {
    return stream(statistics)
        .collect(Collectors.toSet());
  }

  private Statistics createStatistics(String sum, String avg, String max, String min, int count) {
    return new Statistics(
        createAmount(sum),
        createAmount(avg),
        createAmount(max),
        createAmount(min),
        new Count(count)
    );
  }

  private Amount createAmount(String value) {
    return new Amount(new BigDecimal(value));
  }
}
