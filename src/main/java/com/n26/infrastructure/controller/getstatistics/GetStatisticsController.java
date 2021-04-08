package com.n26.infrastructure.controller.getstatistics;

import com.n26.usecase.getstatistics.GetStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class GetStatisticsController {
  private static final Logger LOGGER = LoggerFactory.getLogger(GetStatisticsController.class);

  private final GetStatistics getStatistics;

  public GetStatisticsController(GetStatistics getStatistics) {
    this.getStatistics = getStatistics;
  }

  @GetMapping(value = "/statistics", produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public GetStatisticsControllerResponse getStatistics() {
    LOGGER.info("Received get statistics request.");

    return GetStatisticsControllerResponse.mapFrom(getStatistics.getStatistics());
  }
}
