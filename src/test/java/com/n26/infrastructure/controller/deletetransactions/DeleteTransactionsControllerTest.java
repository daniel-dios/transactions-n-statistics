package com.n26.infrastructure.controller.deletetransactions;

import com.n26.infrastructure.controller.ControllerTest;
import com.n26.usecase.deletetransactions.DeleteTransactions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URI;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteTransactionsControllerTest extends ControllerTest {

  @MockBean
  private DeleteTransactions deleteTransactions;

  @Test
  void shouldReturnNoContent() throws Exception {
    doNothing().when(deleteTransactions).deleteTransactions();

    this.mockMvc
        .perform(delete(new URI("/transactions")))
        .andExpect(status().isNoContent());
  }
}