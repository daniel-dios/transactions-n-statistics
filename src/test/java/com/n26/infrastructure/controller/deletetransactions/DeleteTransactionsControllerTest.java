package com.n26.infrastructure.controller.deletetransactions;

import com.n26.Application;
import com.n26.usecase.deletetransactions.DeleteTransactions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.net.URI;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class DeleteTransactionsControllerTest {

  @Autowired
  private GenericWebApplicationContext webApplicationContext;

  @MockBean
  private DeleteTransactions deleteTransactions;

  private MockMvc mockMvc;

  @BeforeEach
  public void getContext() {
    mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void shouldReturnNoContent() throws Exception {
    doNothing().when(deleteTransactions).deleteTransactions();

    mockMvc.perform(delete(new URI("/transactions")))
        .andExpect(status().isNoContent());
  }
}