package com.n26.usecase.getstatistics;

import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.n26.domain.Statistics.EMPTY_STATISTICS;
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
}
