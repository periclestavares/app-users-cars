package com.veiculos.api.controller;

import com.veiculos.api.dto.UserDTO;
import com.veiculos.api.service.UserService;
import org.json.JSONArray;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Test for the User Controller
 * @author Pericles Tavares
 */

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTest {

    public static final String USERS = "/api/users";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService service;
    @Autowired
    private JacksonTester<UserDTO> userDTOJacksonTester;

    private static UserDTO generateDTOUser() {
        return new UserDTO(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void testGetAll() throws Exception {
        UserDTO userDTO = generateDTOUser();
        Mockito.when(service.getAll()).thenReturn(List.of(userDTO));
        MockHttpServletResponse response = mvc
                .perform(get(USERS))
                .andReturn().getResponse();
        JSONObject content = new JSONArray(response.getContentAsString()).getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(content.get("firstName")).isEqualTo(userDTO.getFirstName());
    }

    @Test
    public void testGetById() throws Exception {
        UserDTO userDTO = generateDTOUser();
        Mockito.when(service.getById(Mockito.any())).thenReturn(userDTO);
        MockHttpServletResponse response = mvc
                .perform(get(USERS + "/1"))
                .andReturn().getResponse();
        String expectedJson = userDTOJacksonTester.write(userDTO).getJson();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    public void testAdd() throws Exception {
        UserDTO userDTO = generateDTOUser();
        Mockito.when(service.add(Mockito.any())).thenReturn(userDTO);
        String expectedJson = userDTOJacksonTester.write(userDTO).getJson();
        MockHttpServletResponse response = mvc
                .perform(post(USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    public void testAddMissingAllFields() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(post(USERS)
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
    public void testAddMissingOneField() throws Exception {
        UserDTO userDTO = generateDTOUser();
        userDTO.setFirstName(null);
        MockHttpServletResponse response = mvc
                .perform(post(USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJacksonTester.write(userDTO).getJson()))
                .andReturn().getResponse();

        JSONObject content = new JSONObject(response.getContentAsString());
        JSONObject error = content.getJSONArray("errors").getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(error.get("errorCode")).isEqualTo("1");
        assertThat(error.get("message")).isEqualTo("Missing fields");
    }

    @Test
    public void testUpdate() throws Exception {
        UserDTO userDTO = generateDTOUser();
        Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(userDTO);
        String expectedJson = userDTOJacksonTester.write(userDTO).getJson();
        MockHttpServletResponse response = mvc
                .perform(put(USERS + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    public void testUpdateMissingId() throws Exception {
        UserDTO userDTO = generateDTOUser();
        Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(userDTO);
        String expectedJson = userDTOJacksonTester.write(userDTO).getJson();
        MockHttpServletResponse response = mvc
                .perform(put(USERS + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testUpdateMissingAllFields() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(put(USERS + "/1")
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
    public void testUpdateMissingOneFields() throws Exception {
        UserDTO userDTO = generateDTOUser();
        userDTO.setPhone(null);
        Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(userDTO);
        String expectedJson = userDTOJacksonTester.write(userDTO).getJson();
        MockHttpServletResponse response = mvc
                .perform(put(USERS + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andReturn().getResponse();

        JSONObject content = new JSONObject(response.getContentAsString());
        JSONObject error = content.getJSONArray("errors").getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(error.get("errorCode")).isEqualTo("1");
        assertThat(error.get("message")).isEqualTo("Missing fields");
    }

    @Test
    public void testDelete() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(delete(USERS + "/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NO_CONTENT.value());
        Mockito.verify(service, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void testDeleteMissingId() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(delete(USERS + "/"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
