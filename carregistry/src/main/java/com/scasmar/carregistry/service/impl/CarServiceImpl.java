package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.entity.BrandEntity;
import com.scasmar.carregistry.model.Car;
import com.scasmar.carregistry.entity.CarEntity;
import com.scasmar.carregistry.respository.BrandRepository;
import com.scasmar.carregistry.respository.CarRepository;
import com.scasmar.carregistry.service.CarService;
import com.scasmar.carregistry.service.converters.BrandConverter;
import com.scasmar.carregistry.service.converters.CarConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarConverter carConverter;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandConverter brandConverter;

    @Override
    @Async
    public CompletableFuture<List<Car>> getAllCars(){
        List<CarEntity> carEntityList = carRepository.findAll();
        List<Car> carList = carEntityList.stream().map(carConverter::toCar).toList();

        return CompletableFuture.completedFuture(carList);
    }

    @Override
    public List<Car> getCarModel(String model) {
        List<CarEntity> carEntityList = carRepository.findByModel(model);

        return carEntityList.stream().map(carConverter::toCar).toList();
    }

    @Override
    public Car addCar(Car car) { // If the brand does not exist the car does not add
        Optional<BrandEntity> brandEntity = brandRepository.findByName(car.getBrand().getName());
        if (brandEntity.isPresent()){
            CarEntity carEntity = carConverter.toEntity(car);
            carEntity.setBrandEntity(brandEntity.get());

            return carConverter.toCar(carRepository.save(carEntity));
        } else {
            return null;
        }
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> addCarList(List<Car> carList){
        List<CarEntity> carEntityList = new ArrayList<>();//carList.stream().map(carConverter::toEntity).toList();
        carList.forEach(car ->{
            Optional<BrandEntity> brandEntity = brandRepository.findByName(car.getBrand().getName());
            if(brandEntity.isPresent()){
                CarEntity carEntity = carConverter.toEntity(car);
                carEntity.setBrandEntity(brandEntity.get());
                carEntityList.add(carEntity);
            }
        });
        List<CarEntity> savedCarEntityList = carRepository.saveAll(carEntityList);
        List<Car> savedCarList = savedCarEntityList.stream().map(carConverter::toCar).toList();

        return CompletableFuture.completedFuture(savedCarList);
    }

    @Override
    public Car updateCar(int id, Car car) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);
        if(carEntityOptional.isPresent()){
            CarEntity carEntity = carConverter.toEntity(car);
            carEntity.setId(id);

            return carConverter.toCar(carRepository.save(carEntity));
        }else {
            return null;
        }
    }

    @Override
    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }
}
