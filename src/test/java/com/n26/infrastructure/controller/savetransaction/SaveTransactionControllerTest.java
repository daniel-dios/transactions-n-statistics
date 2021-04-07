package com.n26.infrastructure.controller.savetransaction;

import com.n26.infrastructure.controller.ControllerTest;
import com.n26.usecase.savetransaction.SaveTransaction;
import com.n26.usecase.savetransaction.SaveTransactionRequest;
import com.n26.usecase.savetransaction.SaveTransactionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static com.n26.usecase.savetransaction.SaveTransactionResponse.FUTURE;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.OLDER;
import static com.n26.usecase.savetransaction.SaveTransactionResponse.PROCESSED;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SaveTransactionControllerTest extends ControllerTest {

  @MockBean
  private SaveTransaction saveTransaction;

  @ParameterizedTest
  @MethodSource("getResponsesAndStatus")
  void shouldReturnExpectedWhenSaveReturns(SaveTransactionResponse response, int expectedStatus) throws Exception {
    final SaveTransactionRequest validRequest =
        new SaveTransactionRequest(new BigDecimal("12.3343"), OffsetDateTime.parse("2018-07-17T09:59:51.312Z"));
    when(saveTransaction.save(validRequest)).thenReturn(response);

    final String validRequestAsJson = "{"
        + "\"amount\":\"12.3343\","
        + "\"timestamp\":\"2018-07-17T09:59:51.312Z\""
        + "}";
    this.mockMvc
        .perform(buildPostWithBody(validRequestAsJson))
        .andExpect(status().is(expectedStatus));
  }

  private static Stream<Arguments> getNonParsableInputs() {
    return Stream.of(
        of("{"
            + "\"amount\":\"12.3343\","
            + "\"timestamp\":\"2018-07-17T\""
            + "}"),
        of("{"
            + "\"amount\":\"12,343\","
            + "\"timestamp\":\"2018-07-17T09:59:51.312Z\""
            + "}")
    );
  }

  @ParameterizedTest
  @MethodSource("getNonParsableInputs")
  void shouldReturnUnprocessableWhenNonParsableInputs(String nonParsableJson) throws Exception {

    this.mockMvc
        .perform(buildPostWithBody(nonParsableJson))
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void shouldReturnUnprocessableWhenTimeStampIsNotZ() throws Exception {
    final String wrongZonedTimestamp = "{"
        + "\"amount\":\"12.3343\","
        + "\"timestamp\":\"2018-07-17T09:59:51.312+01:00\""
        + "}";

    this.mockMvc
        .perform(buildPostWithBody(wrongZonedTimestamp))
        .andExpect(status().isUnprocessableEntity());
  }

  private static Stream<Arguments> getResponsesAndStatus() {
    return Stream.of(
        of(PROCESSED, 201),
        of(OLDER, 204),
        of(FUTURE, 422)
    );
  }

  private MockHttpServletRequestBuilder buildPostWithBody(String json) {
    return post("/transaction")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON);
  }
}
