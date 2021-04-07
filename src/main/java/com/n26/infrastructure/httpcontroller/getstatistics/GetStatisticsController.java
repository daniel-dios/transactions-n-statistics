package com.n26.infrastructure.httpcontroller.getstatistics;

import com.n26.usecase.getstatistics.GetStatistics;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class GetStatisticsController {

  private final GetStatistics getStatistics;

  public GetStatisticsController(GetStatistics getStatistics) {
    this.getStatistics = getStatistics;
  }

  @GetMapping(value = "/statistics", produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public GetStatisticsResponse getStatistics(){
    return GetStatisticsResponse.mapFrom(getStatistics.getStatistics());
  }
}
