package com.scasmar.carregistry.controller.dto;

import lombok.Data;

@Data
public class CarRequest {
    private Integer id;
    private BrandRequest brandRequest;
    private String model;
    private Integer millage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    private String fuelType;
    private Integer numDoors;
}
