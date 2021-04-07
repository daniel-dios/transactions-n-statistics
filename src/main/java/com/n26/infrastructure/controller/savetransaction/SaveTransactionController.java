package com.n26.infrastructure.controller.savetransaction;

import com.n26.usecase.savetransaction.SaveTransaction;
import com.n26.usecase.savetransaction.SaveTransactionRequest;
import com.n26.usecase.savetransaction.SaveTransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SaveTransactionController {

  private final SaveTransaction saveTransaction;

  public SaveTransactionController(SaveTransaction saveTransaction) {
    this.saveTransaction = saveTransaction;
  }

  @PostMapping(value = "/transaction", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> saveTransaction(@RequestBody SaveTransactionBody body) {
    final SaveTransactionResponse save = saveTransaction
        .save(new SaveTransactionRequest(new BigDecimal(body.getAmount()), OffsetDateTime.parse(body.getTimestamp())));

    if (save.equals(SaveTransactionResponse.OLDER)) {
      return ResponseEntity.noContent().build();
    }

    throw new UnsupportedOperationException();
  }
}
