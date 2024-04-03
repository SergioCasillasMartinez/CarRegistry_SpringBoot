package com.scasmar.carregistry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brandEntity;
    private String model;
    private int millage;
    private double price;
    private int year;
    private String description;
    private String colour;
    private String fuelType;
    private int numDoors;
}
