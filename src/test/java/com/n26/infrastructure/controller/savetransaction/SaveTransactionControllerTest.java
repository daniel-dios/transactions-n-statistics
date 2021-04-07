package com.n26.infrastructure.controller.savetransaction;

import com.n26.infrastructure.controller.ControllerTest;
import com.n26.usecase.savetransaction.SaveTransaction;
import com.n26.usecase.savetransaction.SaveTransactionRequest;
import com.n26.usecase.savetransaction.SaveTransactionResponse;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SaveTransactionControllerTest extends ControllerTest {

  @MockBean
  private SaveTransaction saveTransaction;

  private final String validRequestAsJson = "{"
      + "\"amount\":\"12.3343\","
      + "\"timestamp\":\"2018-07-17T09:59:51.312Z\""
      + "}";

  private final SaveTransactionRequest validRequest =
      new SaveTransactionRequest(new BigDecimal("12.3343"), OffsetDateTime.parse("2018-07-17T09:59:51.312Z"));

  @ParameterizedTest
  @MethodSource("getResponsesAndStatus")
  void shouldReturnExpectedWhenSaveTransactionReturns(SaveTransactionResponse response, int expectedStatus)
      throws Exception {
    when(saveTransaction.save(validRequest)).thenReturn(response);

    this.mockMvc
        .perform(buildPostWithBody())
        .andExpect(status().is(expectedStatus));
  }

  private static Stream<Arguments> getResponsesAndStatus() {
    return Stream.of(
        Arguments.of(PROCESSED, 201),
        Arguments.of(OLDER, 204),
        Arguments.of(FUTURE, 422)
    );
  }

  private MockHttpServletRequestBuilder buildPostWithBody() {
    return post("/transaction")
        .content(validRequestAsJson)
        .contentType(MediaType.APPLICATION_JSON);
  }
}
