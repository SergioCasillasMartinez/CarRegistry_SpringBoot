package com.scasmar.carregistry.service;

import com.scasmar.carregistry.entity.CarEntity;
import com.scasmar.carregistry.model.Car;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CarService {
    CompletableFuture<List<Car>> getAllCars();
    List<Car> getCarModel(String model);
    Car addCar(Car carEntity);
    CompletableFuture<List<Car>> addCarList(List<Car> carList);
    Car updateCar(int id, Car carEntity);
    void deleteCar(int id);
}
