package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.entity.BrandEntity;
import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.model.Car;
import com.scasmar.carregistry.entity.CarEntity;
import com.scasmar.carregistry.respository.BrandRepository;
import com.scasmar.carregistry.respository.CarRepository;
import com.scasmar.carregistry.service.CarService;
import com.scasmar.carregistry.service.converts.BrandConverter;
import com.scasmar.carregistry.service.converts.CarConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Car> getAllCars(){
        List<CarEntity> carEntityList = carRepository.findAll();
        List<Car> carList = new ArrayList<>();
        carEntityList.forEach(carEntity -> carList.add(carConverter.toCar(carEntity)));
        return carList;
    }

    @Override
    public List<Car> getCarModel(String model) {
        List<CarEntity> carEntityList = carRepository.findByModel(model);
        List<Car> carList = new ArrayList<>();
        carEntityList.forEach(carEntity -> carList.add(carConverter.toCar(carEntity)));
        return carList;
    }

    @Override
    public Car addCar(Car car) { // If the brand does not exist the car does not add
        Optional<BrandEntity> brandEntityList = brandRepository.findById(car.getBrand().getId());
        if (brandEntityList.isPresent()){
            CarEntity carEntity = carRepository.save(carConverter.toEntity(car));
            carEntity.setBrandEntity(brandEntityList.get());
            return carConverter.toCar(carRepository.save(carEntity));
        } else {
            return null;
        }
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
