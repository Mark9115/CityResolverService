package com.mark;

import com.mark.configurations.ApplicationInitializer;
import com.mark.configurations.WebConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {ApplicationInitializer.class, WebConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void givenWac_whenServletContext_thenItProvidesCityController() {
        ServletContext servletContext = wac.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(wac.getBean("cityController"));
    }

    @Test
    public void givenCityURIWithPost_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(post("/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": \"+79062509999\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.region").value("Санкт-Петербург"))
                .andExpect(status().isOk());
    }
}
