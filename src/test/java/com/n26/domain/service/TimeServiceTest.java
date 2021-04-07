package com.n26.domain.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class TimeServiceTest {

  @Test
  void shouldReturnTimeInUTC() {
    final TimeService timeService = new TimeService();

    assertThat(timeService.getCurrentTime())
        .isCloseTo(OffsetDateTime.now(), Assertions.within(100, ChronoUnit.MILLIS))
        .satisfies(time -> assertThat(time.getOffset()).isEqualTo(ZoneOffset.UTC));
  }
}
