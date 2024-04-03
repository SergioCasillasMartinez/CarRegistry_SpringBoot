package com.scasmar.carregistry.service;

import com.scasmar.carregistry.model.Car;
import java.util.List;

public interface CarService {
    List<Car> getAllCars();
    List<Car> getCarModel(String model);
    Car addCar(Car carEntity);
    Car updateCar(int id, Car carEntity);
    void deleteCar(int id);
}
