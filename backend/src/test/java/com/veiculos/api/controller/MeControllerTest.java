package com.veiculos.api.controller;

import com.veiculos.api.dto.UserDTO;
import com.veiculos.api.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Test for the MeController
 * @author Pericles Tavares
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MeControllerTest {

    public static final String ME = "/api/me";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService service;

    @Autowired
    private JacksonTester<UserDTO> userDTOJacksonTester;

    @Test
    @WithMockUser
    public void testGetLoggedUser() throws Exception {
        UserDTO userDTO = new UserDTO(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(service.getLoggedUser()).thenReturn(userDTO);

        MockHttpServletResponse response = mvc
                .perform(get(ME))
                .andReturn().getResponse();

        String expectedJson = userDTOJacksonTester.write(userDTO).getJson();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    public void testGetLoggedUserWithNoLoggedUser() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(get(ME))
                .andReturn().getResponse();

        JSONObject content = new JSONObject(response.getContentAsString());
        JSONObject error = content.getJSONArray("errors").getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.get("errorCode")).isEqualTo("6");
        assertThat(error.get("message")).isEqualTo("Unauthorized");
    }

    @Test
    public void testGetLoggedUserWithExpiredToken() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(get(ME))
                .andReturn().getResponse();

        JSONObject content = new JSONObject(response.getContentAsString());
        JSONObject error = content.getJSONArray("errors").getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(error.get("errorCode")).isEqualTo("6");
        assertThat(error.get("message")).isEqualTo("Unauthorized");
    }
}
