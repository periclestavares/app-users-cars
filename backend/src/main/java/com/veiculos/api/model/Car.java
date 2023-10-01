package com.veiculos.api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

/**
 * Model for the Car entity
 * @author Péricles Tavares
 */
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int carYear;

    private String licensePlate;

    private String model;

    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
