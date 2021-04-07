package com.n26.infrastructure.controller;

import com.n26.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.support.GenericWebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public abstract class ControllerTest {

  @Autowired
  private GenericWebApplicationContext webApplicationContext;

  protected MockMvc mockMvc;

  @BeforeEach
  public void getContext() {
    mockMvc = webAppContextSetup(webApplicationContext).build();
  }
}
