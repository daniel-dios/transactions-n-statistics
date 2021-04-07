package com.n26.infrastructure.controller.savetransaction;

import com.n26.usecase.savetransaction.SaveTransaction;
import com.n26.usecase.savetransaction.SaveTransactionRequest;
import com.n26.usecase.savetransaction.SaveTransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class SaveTransactionController {

  private final SaveTransaction saveTransaction;

  public SaveTransactionController(SaveTransaction saveTransaction) {
    this.saveTransaction = saveTransaction;
  }

  @PostMapping(value = "/transactions", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> saveTransaction(@RequestBody SaveTransactionBody body) {
    final BigDecimal amount = getAmount(body);
    final OffsetDateTime parse = getDateTime(body);

    final SaveTransactionResponse save = saveTransaction.save(new SaveTransactionRequest(amount, parse));

    return status(mapToStatus(save)).build();
  }

  private OffsetDateTime getDateTime(SaveTransactionBody body) {
    try {
      final OffsetDateTime parsed = OffsetDateTime.parse(body.getTimestamp());
      if (parsed.getOffset().equals(ZoneOffset.UTC)){
        return parsed;
      }
      throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
    } catch (DateTimeException ex) {
      throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
    }
  }

  private BigDecimal getAmount(SaveTransactionBody body) {
    try {
      return new BigDecimal(body.getAmount());
    } catch (NumberFormatException ex) {
      throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
    }
  }

  private HttpStatus mapToStatus(SaveTransactionResponse save) {
    switch (save) {
      case PROCESSED:
        return CREATED;
      case OLDER:
        return NO_CONTENT;
      case FUTURE:
        return UNPROCESSABLE_ENTITY;
    }

    // its not gonna happen but java 8...
    return I_AM_A_TEAPOT;
  }
}
