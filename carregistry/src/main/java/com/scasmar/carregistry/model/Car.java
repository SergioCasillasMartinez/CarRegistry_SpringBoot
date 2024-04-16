package com.scasmar.carregistry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int id;
    private Brand brand;
    private String model;
    private int millage;
    private double price;
    private int year;
    private String description;
    private String colour;
    private String fuelType;
    private int numDoors;
}
