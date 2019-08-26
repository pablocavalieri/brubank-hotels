/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.brubank.hotels;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.brubank.hotels.deploy.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
@AutoConfigureMockMvc
public class AppTest {

  @Autowired
  private MockMvc mvcEntryPoint;

  @Test
  public void smokeTest() throws Exception {
    mvcEntryPoint.perform(get("/reservationsForDestination?city=Buenos Aires&country=Argentina"))
        .andExpect(status().isOk());
  }
}