package com.scasmar.carregistry.service.converts;

import com.scasmar.carregistry.model.Car;
import com.scasmar.carregistry.entity.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarConverter {
    @Autowired
    private BrandConverter brandConverter;

    public Car toCar(CarEntity entity){
        Car car = new Car();
        car.setBrand(brandConverter.toBrand(entity.getBrandEntity()));
        car.setId(entity.getId());
        car.setModel(entity.getModel());
        car.setMillage(entity.getMillage());
        car.setPrice(entity.getPrice());
        car.setYear(entity.getYear());
        car.setDescription(entity.getDescription());
        car.setColour(entity.getColour());
        car.setFuelType(entity.getFuelType());
        car.setNumDoors(entity.getNumDoors());

        return car;
    }

    public CarEntity toEntity(Car entity){
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carEntity.getId());
        carEntity.setBrandEntity(brandConverter.toEntity(entity.getBrand()));
        carEntity.setModel(entity.getModel());
        carEntity.setMillage(entity.getMillage());
        carEntity.setPrice(entity.getPrice());
        carEntity.setYear(entity.getYear());
        carEntity.setDescription(entity.getDescription());
        carEntity.setColour(entity.getColour());
        carEntity.setFuelType(entity.getFuelType());
        carEntity.setNumDoors(entity.getNumDoors());

        return carEntity;
    }
}
