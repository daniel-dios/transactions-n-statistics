package com.n26.infrastructure.controller.savetransaction;

import com.n26.usecase.savetransaction.SaveTransaction;
import com.n26.usecase.savetransaction.SaveTransactionRequest;
import com.n26.usecase.savetransaction.SaveTransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.IM_USED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.unprocessableEntity;

@RestController
public class SaveTransactionController {
  private static final Logger LOGGER = LoggerFactory.getLogger(SaveTransactionController.class);

  private final SaveTransaction saveTransaction;

  public SaveTransactionController(SaveTransaction saveTransaction) {
    this.saveTransaction = saveTransaction;
  }

  @PostMapping(value = "/transactions", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> saveTransaction(@RequestBody SaveTransactionBody body) {
    LOGGER.info("Received save transaction request amount.");

    final BigDecimal amount = getAmount(body.getAmount());
    final OffsetDateTime timestamp = getTimestamp(body.getTimestamp());

    final SaveTransactionResponse result = saveTransaction.save(new SaveTransactionRequest(amount, timestamp));

    return mapToResponse(result);
  }

  private OffsetDateTime getTimestamp(String timestamp) {
    try {
      final OffsetDateTime parsed = OffsetDateTime.parse(timestamp);
      if (parsed.getOffset().equals(UTC)) {
        return parsed;
      }
      LOGGER.info("Received non UTC timestamp {}.", timestamp);
      throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
    } catch (DateTimeException ex) {
      LOGGER.info("Received non parsable timestamp {}.", timestamp);

      throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
    }
  }

  private BigDecimal getAmount(String amount) {
    try {
      return new BigDecimal(amount);
    } catch (NumberFormatException ex) {
      LOGGER.info("Received incorrect amount {}.", amount);
      throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
    }
  }

  /*
   * Hi! Since this challenge was written in java 8 there is no possibility to avoid that default return
   * statement even if we are mapping an enum with a switch clause, the code is not reachable because all
   * enum values are mapped. Next java versions are taking this into account and avoiding this default when
   * switching enums.
   */
  private ResponseEntity<Object> mapToResponse(SaveTransactionResponse save) {
    switch (save) {
      case PROCESSED:
        return status(CREATED).build();
      case OLDER:
        return noContent().build();
      case FUTURE:
        return unprocessableEntity().build();
      default:
        // compiler wants it :_(
        return status(IM_USED).build();
    }
  }
}
