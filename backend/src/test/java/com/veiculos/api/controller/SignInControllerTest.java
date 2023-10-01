package com.veiculos.api.controller;

import com.veiculos.api.infra.authentication.service.AuthenticationService;
import com.veiculos.api.infra.authentication.service.JWTAuthenticationService;
import com.veiculos.api.model.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Test for the SignIn Controller
 * @author Pericles Tavares
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class SignInControllerTest {

    public static final String SIGN_IN = "/api/signin";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthenticationManager manager;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JWTAuthenticationService tokenService;

    @Test
    public void testSignIn() throws Exception {
        User user = new User();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        Mockito.when(manager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(tokenService.generateToken(Mockito.any())).thenReturn("generatedToken");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", "login");
        jsonObject.put("password", "password");
        MockHttpServletResponse response = mvc
                .perform(post(SIGN_IN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andReturn().getResponse();

        JSONObject content = new JSONObject(response.getContentAsString());
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(content.get("token")).isEqualTo("generatedToken");
    }

    @Test
    public void testSignInMissingFields() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(post(SIGN_IN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        JSONObject content = new JSONObject(response.getContentAsString());
        JSONObject error = content.getJSONArray("errors").getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(error.get("errorCode")).isEqualTo("1");
        assertThat(error.get("message")).isEqualTo("Missing fields");
    }

    @Test
    public void testSignInMissingLogin() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", "password");

        MockHttpServletResponse response = mvc
                .perform(post(SIGN_IN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andReturn().getResponse();

        JSONObject content = new JSONObject(response.getContentAsString());
        JSONObject error = content.getJSONArray("errors").getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(error.get("errorCode")).isEqualTo("1");
        assertThat(error.get("message")).isEqualTo("Missing fields");
    }
}
