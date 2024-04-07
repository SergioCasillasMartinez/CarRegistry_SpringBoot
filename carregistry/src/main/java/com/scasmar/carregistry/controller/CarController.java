package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.controller.dto.CarRequest;
import com.scasmar.carregistry.controller.dto.CarResponse;
import com.scasmar.carregistry.controller.mapper.CarMapper;
import com.scasmar.carregistry.model.Car;
import com.scasmar.carregistry.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private CarMapper carMapper;

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public CompletableFuture<?> getAllCars(){
        try{
            CompletableFuture<List<Car>> carsList = carService.getAllCars();
            List<CarResponse> carResponseList = carsList.get().stream().map(carMapper::toResponse).toList();

            return CompletableFuture.completedFuture(ResponseEntity.ok().body(carResponseList));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @GetMapping("/{model}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> getCarPrice(@PathVariable String model){
        List<Car> carsList = carService.getCarModel(model);
        List<CarResponse> carResponseList = carsList.stream().map(carMapper::toResponse).toList();

        return ResponseEntity.ok().body(carResponseList);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> addCar(@RequestBody CarRequest carRequest){
        CarResponse carResponse = carMapper.toResponse(carService.addCar(carMapper.toModel(carRequest)));

        return ResponseEntity.ok().body(carResponse);
    }

    @PostMapping("/addList")
    @PreAuthorize("hasRole('VENDOR')")
    public CompletableFuture<?> addCarList(@RequestBody List<CarRequest> carRequestList){
        try{
            List<Car> carList = carRequestList.stream().map(carMapper::toModel).toList();

            return carService.addCarList(carList).thenApply(ResponseEntity::ok);
        }catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.ok().body(e));
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody CarRequest carRequest){
        carService.updateCar(id, carMapper.toModel(carRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteCar(@PathVariable int id){
        carService.deleteCar(id);
        return ResponseEntity.ok().body("Deleted Car with id: " + id);
    }
}
