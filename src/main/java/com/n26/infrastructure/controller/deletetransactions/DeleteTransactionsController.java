package com.n26.infrastructure.controller.deletetransactions;

import com.n26.usecase.deletetransactions.DeleteTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
public class DeleteTransactionsController {
  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTransactionsController.class);

  private final DeleteTransactions deleteTransactions;

  public DeleteTransactionsController(DeleteTransactions deleteTransactions) {
    this.deleteTransactions = deleteTransactions;
  }

  @DeleteMapping("/transactions")
  @ResponseStatus(NO_CONTENT)
  public void deleteTransactions() {
    LOGGER.info("Received delete transactions request.");

    deleteTransactions.deleteTransactions();
  }
}
