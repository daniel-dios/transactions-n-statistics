package com.n26.domain.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class TimeService {
  private static final ZoneId UTC = ZoneId.of("UTC");

  public OffsetDateTime getCurrentTime() {
    return OffsetDateTime.now(UTC);
  }
}
