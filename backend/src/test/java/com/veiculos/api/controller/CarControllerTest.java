package com.veiculos.api.controller;

import com.veiculos.api.dto.CarDTO;
import com.veiculos.api.service.CarService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Test for the Car Controller
 * @author Pericles Tavares
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

    public static final String CARS = "/api/cars";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CarService service;
    @Autowired
    private JacksonTester<CarDTO> carDTOJacksonTester;

    private static CarDTO generateDTOCar() {
        return new CarDTO(null, 2023, "123456", "Model", "Color");
    }

    @Test
    @WithMockUser
    public void testGetAll() throws Exception {
        CarDTO carDTO = generateDTOCar();
        Mockito.when(service.getAll()).thenReturn(List.of(carDTO));
        MockHttpServletResponse response = mvc
                .perform(get(CARS))
                .andReturn().getResponse();
        JSONObject content = new JSONArray(response.getContentAsString()).getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(content.get("licensePlate")).isEqualTo(carDTO.getLicensePlate());
    }

    @Test
    @WithMockUser
    public void testGetById() throws Exception {
        CarDTO userDTO = generateDTOCar();
        Mockito.when(service.getById(Mockito.any())).thenReturn(userDTO);
        MockHttpServletResponse response = mvc
                .perform(get(CARS + "/1"))
                .andReturn().getResponse();
        String expectedJson = carDTOJacksonTester.write(userDTO).getJson();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @WithMockUser
    public void testAdd() throws Exception {
        CarDTO userDTO = generateDTOCar();
        Mockito.when(service.add(Mockito.any())).thenReturn(userDTO);
        String expectedJson = carDTOJacksonTester.write(userDTO).getJson();
        MockHttpServletResponse response = mvc
                .perform(post(CARS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @WithMockUser
    public void testAddMissingAllFields() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(post(CARS)
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
    @WithMockUser
    public void testAddMissingOneField() throws Exception {
        CarDTO carDTO = generateDTOCar();
        carDTO.setLicensePlate(null);
        MockHttpServletResponse response = mvc
                .perform(post(CARS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carDTOJacksonTester.write(carDTO).getJson()))
                .andReturn().getResponse();

        JSONObject content = new JSONObject(response.getContentAsString());
        JSONObject error = content.getJSONArray("errors").getJSONObject(0);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(error.get("errorCode")).isEqualTo("1");
        assertThat(error.get("message")).isEqualTo("Missing fields");
    }

    @Test
    @WithMockUser
    public void testUpdate() throws Exception {
        CarDTO carDTO = generateDTOCar();
        Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(carDTO);
        String expectedJson = carDTOJacksonTester.write(carDTO).getJson();
        MockHttpServletResponse response = mvc
                .perform(put(CARS + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @WithMockUser
    public void testUpdateMissingId() throws Exception {
        CarDTO carDTO = generateDTOCar();
        Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(carDTO);
        String expectedJson = carDTOJacksonTester.write(carDTO).getJson();
        MockHttpServletResponse response = mvc
                .perform(put("/cars/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser
    public void testUpdateMissingAllFields() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(put(CARS + "/1")
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
    @WithMockUser
    public void testUpdateMissingOneFields() throws Exception {
        CarDTO carDTO = generateDTOCar();
        carDTO.setLicensePlate(null);
        Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(carDTO);
        String expectedJson = carDTOJacksonTester.write(carDTO).getJson();
        MockHttpServletResponse response = mvc
                .perform(put(CARS + "/1")
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
    @WithMockUser
    public void testDelete() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(delete(CARS + "/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NO_CONTENT.value());
        Mockito.verify(service, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @WithMockUser
    public void testDeleteMissingId() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(delete(CARS + "/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
