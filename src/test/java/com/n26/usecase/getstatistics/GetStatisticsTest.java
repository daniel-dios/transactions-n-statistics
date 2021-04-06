package com.n26.usecase.getstatistics;

import com.n26.domain.Amount;
import com.n26.domain.Count;
import com.n26.domain.Statistics;
import com.n26.domain.StatisticsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static java.util.Collections.emptySet;
import static org.mockito.Mockito.when;

public class GetStatisticsTest {

  @Test
  void shouldReturnEmptyStatisticsWhenThereIsNoStatisticsInRepo() {
    final StatisticsRepository statisticsRepository = Mockito.mock(StatisticsRepository.class);
    when(statisticsRepository.getStatistics()).thenReturn(emptySet());
    final GetStatistics getStatistics = new GetStatistics(statisticsRepository);

    final Statistics actual = getStatistics.getStatistics();

    final Amount zero = new Amount(new BigDecimal("0"));
    final Statistics expected = new Statistics(zero, zero, zero, zero, new Count(0));
    Assertions
        .assertThat(actual)
        .isEqualToComparingFieldByField(expected);
  }
}
