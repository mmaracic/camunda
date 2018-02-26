/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camunda.test.controller;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Marijo
 */
@Slf4j
@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
public class TextControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext context;

    @Test
    public void checkController() {
        TextController controller = context.getBean(TextController.class);
        Assert.assertNotNull(controller, "Controller not preset. Context problem");
    }

    @Test
    @Commit
    public void submitText() throws Exception {
        String content = "Today is a nice day.";
        MvcResult result = mockMvc.perform(post("/text/submit").accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.TEXT_PLAIN).content(content))
                .andExpect(status().isOk()).andReturn();
        String resultBody = new String(result.getResponse().getContentAsByteArray(),StandardCharsets.UTF_8);
        log.info("Result: " + resultBody);
    }
}
