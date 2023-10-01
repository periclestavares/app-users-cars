package com.veiculos.api.service;

import com.veiculos.api.dto.CarDTO;
import com.veiculos.api.infra.exception.FieldExistsException;
import com.veiculos.api.model.Car;
import com.veiculos.api.model.User;
import com.veiculos.api.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

/**
 * Test for the Car Service
 * @author Pericles Tavares
 */
@SpringBootTest
public class CarServiceTest {
    @Autowired
    private CarService service;
    @MockBean
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        User user = new User(1L, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    private static Car generateCar() {
        return new Car(null, 2023, "123456", "Model", "Color", new User());
    }

    private static void validateUserDTO(CarDTO carDTO, Car car) {
        assertThat(carDTO.getCarYear()).isEqualTo(car.getCarYear());
        assertThat(carDTO.getLicensePlate()).isEqualTo(car.getLicensePlate());
        assertThat(carDTO.getModel()).isEqualTo(car.getModel());
        assertThat(carDTO.getColor()).isEqualTo(car.getColor());
    }

    @Test
    public void testGetAll() {
        Car car = generateCar();
        Mockito.when(carRepository.findAllByUserId(1L)).thenReturn(List.of(car));
        List<CarDTO> list = service.getAll();
        assertThat(list.size()).isEqualTo(1);
        list.forEach(carDTO -> {
            validateUserDTO(carDTO, car);
        });
    }


    @Test
    public void testGetById() {
        Car car = generateCar();
        Mockito.when(carRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(car));
        CarDTO carDTO = service.getById(1L);
        validateUserDTO(carDTO, car);
    }

    @Test
    public void testAdd() throws FieldExistsException {
        Car car = generateCar();
        car.setId(1L);
        CarDTO carDTO = new CarDTO(null, 2023, "123456", "Model", "Color");
        Mockito.when(carRepository.save(Mockito.any())).thenReturn(car);
        CarDTO addedCarDTO = service.add(carDTO);
        validateUserDTO(addedCarDTO, car);
    }

    @Test
    public void testAddWithExistingLicensePlate() {
        CarDTO carDTO = new CarDTO(null, 2023, "123456", "Model", "Color");
        Mockito.when(carRepository.existsByLicensePlate(Mockito.any())).thenReturn(true);
        FieldExistsException e = catchThrowableOfType(() -> service.add(carDTO), FieldExistsException.class);
        assertThat(e.getFields().get(4L)).isEqualTo("License Plate");
    }

    @Test
    public void testUpdate() throws FieldExistsException {
        Car car = generateCar();
        car.setId(1L);
        CarDTO carDTO = new CarDTO(null, 2023, "123456", "Model", "Color");
        Mockito.when(carRepository.save(Mockito.any())).thenReturn(car);
        CarDTO updatedCarDTO = service.update(1L, carDTO);
        validateUserDTO(updatedCarDTO, car);
    }

    @Test
    public void testUpdateWithExistingLicensePlate() {
        CarDTO carDTO = new CarDTO(null, 2023, "123456", "Model", "Color");
        Mockito.when(carRepository.existsByLicensePlateAndIdNot(Mockito.any(), Mockito.any())).thenReturn(true);
        FieldExistsException e = catchThrowableOfType(() -> service.update(1L, carDTO), FieldExistsException.class);
        assertThat(e.getFields().get(4L)).isEqualTo("License Plate");
    }

    @Test
    public void testDelete()  {
        service.delete(1L);
        Mockito.verify(carRepository, Mockito.times(1)).deleteById(Mockito.any());
    }
}
