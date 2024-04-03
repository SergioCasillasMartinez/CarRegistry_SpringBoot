package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.controller.dto.CarRequest;
import com.scasmar.carregistry.controller.dto.CarResponse;
import com.scasmar.carregistry.controller.mapper.CarMapper;
import com.scasmar.carregistry.model.Car;
import com.scasmar.carregistry.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private CarMapper carMapper;

    @GetMapping("/allCars")
    public ResponseEntity<?> getAllCars(){
        List<Car> carsList = carService.getAllCars();
        List<CarResponse> carResponseList = new ArrayList<>();
        carsList.forEach(car -> carResponseList.add(carMapper.toResponse(car)));
        return ResponseEntity.ok().body(carResponseList);
    }

    @GetMapping("/carModel/{model}")
    public ResponseEntity<?> getCarPrice(@PathVariable String model){
        List<Car> carsList = carService.getCarModel(model);
        List<CarResponse> carResponseList = new ArrayList<>();
        carsList.forEach(car -> carResponseList.add(carMapper.toResponse(car)));
        return ResponseEntity.ok().body(carResponseList);
    }

    @PostMapping("/addCar")
    public ResponseEntity<?> addCar(@RequestBody CarRequest carRequest){
        CarResponse carResponse = carMapper.toResponse(carService.addCar(carMapper.toModel(carRequest)));
        return ResponseEntity.ok().body(carResponse);
    }

    @PutMapping("/updateCar/{id}")
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody CarRequest carRequest){
        carService.updateCar(id, carMapper.toModel(carRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id){
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
